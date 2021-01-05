import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import AppNavbar from '../main/AppNavbar';
import {Link} from 'react-router-dom';

class BookList extends Component {

    constructor(props) {
        super(props);
        this.state = {books: [], isLoading: true};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('books')
            .then(response => response.json())
            .then(data => this.setState({books: data, isLoading: false}));
    }

    async remove(id) {
        await fetch(`/books/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedBooks = [...this.state.books].filter(i => i.id !== id);
            this.setState({books: updatedBooks});
        });
    }

    render() {
        const {books, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const bookList = books.map(book => {
            return <tr key={book.id}>
                <td>{book.id}</td>
                <td style={{whiteSpace: 'nowrap'}}>{book.name}</td>
                <td>{book.genre}</td>
                <td>{book.isbn}</td>
                <td>{book.publishingHouseId}</td>
                <td>{book.authorsIds.map(id => {
                    return <div key={id}>{id}</div>
                })}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/books/" + book.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(book.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/books/new">Add Book</Button>
                    </div>
                    <h3>All books</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="5%">ID</th>
                            <th width="15%">Name</th>
                            <th width="20%">Genre</th>
                            <th width="15%">ISBN</th>
                            <th width="15%">Publishing house ID</th>
                            <th>Authors IDs</th>
                            <th width="10%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {bookList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default BookList;