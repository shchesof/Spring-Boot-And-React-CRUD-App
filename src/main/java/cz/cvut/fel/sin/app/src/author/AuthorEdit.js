import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from '../main/AppNavbar';
import styled from "@emotion/styled"

const Error = styled.p`
    padding-top: 2px;
    margin: 0;
    color: red;
`

class AuthorEdit extends Component {

    emptyItem = {
        firstName: '',
        lastName: '',
        email: '',
        firstNameBlankError: false,
        firstNameFormatError: false,
        lastNameBlankError: false,
        lastNameFormatError: false,
        emailBlankError: false,
        emailFormatError: false
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const author = await (await fetch(`/authors/${this.props.match.params.id}`)).json();
            this.setState({item: author});
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        if (this.validateState()) {
            event.preventDefault();
            const {item} = this.state;

            await fetch('/authors' + (item.id ? '/' + item.id : ''), {
                method: (item.id) ? 'PUT' : 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(item),
            });
            this.props.history.push('/authors');
        }
    }

    validateState() {
        let ret = true;
        if(this.state.item.firstName.trim() === '') {
            this.setState(prevState  => {
                let item = {...prevState.item};
                item.firstNameBlankError = true;
                return {item};
            });
            ret = false;
        }
        else {
            this.setState(prevState  => {
                let item = {...prevState.item};
                item.firstNameBlankError = false;
                return {item};
            });
            if (!/^[a-zA-Z]+$/.test(this.state.item.firstName)) {
                this.setState(prevState  => {
                    let item = {...prevState.item};
                    item.firstNameFormatError = true;
                    return {item};
                });
                ret = false;
            } else this.setState(prevState  => {
                let item = {...prevState.item};
                item.firstNameFormatError = false;
                return {item};
            });
        }
        if(this.state.item.lastName.trim() === '') {
            this.setState(prevState  => {
                let item = {...prevState.item};
                item.lastNameBlankError = true;
                return {item};
            });
            ret = false;
        }
        else {
            this.setState(prevState  => {
                let item = {...prevState.item};
                item.lastNameBlankError = false;
                return {item};
            });
            if (!/^[a-zA-Z]+$/.test(this.state.item.lastName)) {
                this.setState(prevState  => {
                    let item = {...prevState.item};
                    item.lastNameFormatError = true;
                    return {item};
                });
                ret = false;
            } else this.setState(prevState  => {
                let item = {...prevState.item};
                item.lastNameFormatError = false;
                return {item};
            });
        }
        if(this.state.item.email.trim() === '') {
            this.setState(prevState  => {
                let item = {...prevState.item};
                item.emailBlankError = true;
                return {item};
            });
            ret = false;
        }
        else {
            this.setState(prevState  => {
                let item = {...prevState.item};
                item.emailBlankError = false;
                return {item};
            });
            if (!/^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(this.state.item.email)) {
                this.setState(prevState  => {
                    let item = {...prevState.item};
                    item.emailFormatError = true;
                    return {item};
                });
                ret = false;
            } else this.setState(prevState  => {
                let item = {...prevState.item};
                item.emailFormatError = false;
                return {item};
            });
        }
        return ret;
    }

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit Author' : 'Add Author'}</h2>;

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="firstName">First name</Label>
                        <Input type="text" name="firstName" id="firstName" value={item.firstName || ''}
                               onChange={this.handleChange} autoComplete="firstName"/>
                        {item.firstNameBlankError && <Error>First name cannot be blank</Error>}
                        {item.firstNameFormatError && <Error>First name can only contain letters</Error>}
                    </FormGroup>
                    <FormGroup>
                        <Label for="lastName">Last name</Label>
                        <Input type="text" name="lastName" id="lastName" value={item.lastName || ''}
                               onChange={this.handleChange} autoComplete="lastName"/>
                        {item.lastNameBlankError && <Error>Last name cannot be blank</Error>}
                        {item.lastNameFormatError && <Error>Last name can only contain letters</Error>}
                    </FormGroup>
                    <FormGroup>
                        <Label for="email">Email</Label>
                        <Input type="text" name="email" id="email" value={item.email || ''}
                               onChange={this.handleChange} autoComplete="email"/>
                        {item.emailBlankError && <Error>Email cannot be blank</Error>}
                        {item.emailFormatError && <Error>Wrong email format</Error>}
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/authors">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(AuthorEdit);