import React, {Component} from 'react';
import '../main/App.css';
import {Link, withRouter} from "react-router-dom";
import AppNavbar from "../main/AppNavbar";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import styled from "@emotion/styled"

const Error = styled.p`
    padding-top: 2px;
    margin: 0;
    color: red;
`

class AddBook extends Component {

    emptyItem = {
        name: '',
        address: '',
        books: [],
        bookId: '',
        idBlankError: false,
        idFormatError: false
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
        const library = await (await fetch(`/libraries/${this.props.match.params.id}`)).json();
        this.setState({item: library});
    }

    async handleSubmit(event) {
        if (this.validateState()) {
            event.preventDefault();
            const {item} = this.state;

            await fetch(`/libraries/${item.bookId}/${this.props.match.params.id}`, {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(item),
            });
            this.props.history.push('/libraries');
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

    validateState() {
        let ret = true;
        if(this.state.item.bookId.trim() === '') {
            this.setState(prevState  => {
                let item = {...prevState.item};
                item.idBlankError = true;
                return {item};
            });
            ret = false;
        }
        else {
            this.setState(prevState  => {
                let item = {...prevState.item};
                item.idBlankError = false;
                return {item};
            });
            if (!/^[0-9]+$/.test(this.state.item.bookId)) {
                this.setState(prevState  => {
                    let item = {...prevState.item};
                    item.idFormatError = true;
                    return {item};
                });
                ret = false;
            } else this.setState(prevState  => {
                let item = {...prevState.item};
                item.idFormatError = false;
                return {item};
            });
        }
        return ret;
    }

    render() {
        const {item} = this.state;
        const title = <h2>{'Add book'}</h2>;

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="bookId">Book id</Label>
                        <Input type="text" name="bookId" id="bookId" value={item.bookId || ''}
                               onChange={this.handleChange} autoComplete="bookId"/>
                        {item.idBlankError && <Error>ID cannot be blank</Error>}
                        {item.idFormatError && <Error>ID can only contain numbers</Error>}
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

export default withRouter(AddBook);