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

class BookEdit extends Component {

    emptyItem = {
        name: '',
        genre: '',
        isbn: '',
        nameBlankError: false,
        nameFormatError: false,
        genreBlankError: false,
        genreFormatError: false,
        isbnBlankError: false,
        isbnFormatError: false
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
            const book = await (await fetch(`/books/${this.props.match.params.id}`)).json();
            this.setState({item: book});
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

            await fetch('/books' + (item.id ? '/' + item.id : ''), {
                method: (item.id) ? 'PUT' : 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(item),
            });
            this.props.history.push('/books');
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
        if(this.state.item.genre.trim() === '') {
            this.setState(prevState  => {
                let item = {...prevState.item};
                item.genreBlankError = true;
                return {item};
            });
            ret = false;
        }
        else {
            this.setState(prevState  => {
                let item = {...prevState.item};
                item.genreBlankError = false;
                return {item};
            });
            if (!/^[a-zA-Z]+$/.test(this.state.item.genre)) {
                this.setState(prevState  => {
                    let item = {...prevState.item};
                    item.genreFormatError = true;
                    return {item};
                });
                ret = false;
            } else this.setState(prevState  => {
                let item = {...prevState.item};
                item.genreFormatError = false;
                return {item};
            });
        }
        if(this.state.item.isbn.trim() === '') {
            this.setState(prevState  => {
                let item = {...prevState.item};
                item.isbnBlankError = true;
                return {item};
            });
            ret = false;
        }
        else {
            this.setState(prevState  => {
                let item = {...prevState.item};
                item.isbnBlankError = false;
                return {item};
            });
            if (!/^[0-9]+$/.test(this.state.item.isbn)) {
                this.setState(prevState  => {
                    let item = {...prevState.item};
                    item.isbnFormatError = true;
                    return {item};
                });
                ret = false;
            } else this.setState(prevState  => {
                let item = {...prevState.item};
                item.isbnFormatError = false;
                return {item};
            });
        }
        return ret;
    }

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit Book' : 'Add Book'}</h2>;

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit} autoComplete="off">
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                               onChange={this.handleChange}/>
                        {item.nameBlankError && <Error>Name cannot be blank</Error>}
                        {item.nameFormatError && <Error>Name can only contain letters</Error>}
                    </FormGroup>
                    <FormGroup>
                        <Label for="genre">Genre</Label>
                        <Input type="text" name="genre" id="genre" value={item.genre || ''}
                               onChange={this.handleChange}/>
                        {item.genreBlankError && <Error>Genre cannot be blank</Error>}
                        {item.genreFormatError && <Error>Genre can only contain letters</Error>}
                    </FormGroup>
                    <FormGroup>
                        <Label for="isbn">ISBN</Label>
                        <Input type="text" name="isbn" id="isbn" value={item.isbn || ''}
                               onChange={this.handleChange}/>
                        {item.isbnBlankError && <Error>ISBN cannot be blank</Error>}
                        {item.isbnFormatError && <Error>ISBN can only contain numbers</Error>}
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/books">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(BookEdit);