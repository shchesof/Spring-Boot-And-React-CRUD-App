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

class LibraryEdit extends Component {

    emptyItem = {
        name: '',
        address: '',
        nameBlankError: false,
        nameFormatError: false,
        addressBlankError: false,
        addressFormatError: false
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
            const library = await (await fetch(`/libraries/${this.props.match.params.id}`)).json();
            this.setState({item: library});
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

            await fetch('/libraries' + (item.id ? '/' + item.id : ''), {
                method: (item.id) ? 'PUT' : 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(item),
            });
            this.props.history.push('/libraries');
        }
    }

    validateState() {
        let ret = true;
        if(this.state.item.name.trim() === '') {
            this.setState(prevState  => {
                let item = {...prevState.item};
                item.nameBlankError = true;
                return {item};
            });
            ret = false;
        }
        else {
            this.setState(prevState  => {
                let item = {...prevState.item};
                item.nameBlankError = false;
                return {item};
            });
            if (!/^[0-9a-zA-Z\s]+$/.test(this.state.item.name)) {
                this.setState(prevState  => {
                    let item = {...prevState.item};
                    item.nameFormatError = true;
                    return {item};
                });
                ret = false;
            } else this.setState(prevState  => {
                let item = {...prevState.item};
                item.nameFormatError = false;
                return {item};
            });
        }
        if(this.state.item.address.trim() === '') {
            this.setState(prevState  => {
                let item = {...prevState.item};
                item.addressBlankError = true;
                return {item};
            });
            ret = false;
        }
        else {
            this.setState(prevState  => {
                let item = {...prevState.item};
                item.addressBlankError = false;
                return {item};
            });
            if (!/^[0-9a-zA-Z\s]+$/.test(this.state.item.address)) {
                this.setState(prevState  => {
                    let item = {...prevState.item};
                    item.addressFormatError = true;
                    return {item};
                });
                ret = false;
            } else this.setState(prevState  => {
                let item = {...prevState.item};
                item.addressFormatError = false;
                return {item};
            });
        }
        return ret;
    }

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit Library' : 'Add Library'}</h2>;

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                        {item.nameBlankError && <Error>Name cannot be blank</Error>}
                        {item.nameFormatError && <Error>Name can only contain letters</Error>}
                    </FormGroup>
                    <FormGroup>
                        <Label for="address">Address</Label>
                        <Input type="text" name="address" id="address" value={item.address || ''}
                               onChange={this.handleChange} autoComplete="address"/>
                        {item.addressBlankError && <Error>Address cannot be blank</Error>}
                        {item.addressFormatError && <Error>Address can only contain letters</Error>}
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/libraries">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(LibraryEdit);