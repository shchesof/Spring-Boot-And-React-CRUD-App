import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import AppNavbar from '../main/AppNavbar';
import {Link} from 'react-router-dom';

class AuthorList extends Component {

    constructor(props) {
        super(props);
        this.state = {authors: [], isLoading: true};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('authors')
            .then(response => response.json())
            .then(data => this.setState({authors: data, isLoading: false}));
    }

    async remove(id) {
        await fetch(`/authors/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedAuthors = [...this.state.authors].filter(i => i.id !== id);
            this.setState({authors: updatedAuthors});
        });
    }

    render() {
        const {authors, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const authorList = authors.map(author => {
            return <tr key={author.id}>
                <td>{author.id}</td>
                <td style={{whiteSpace: 'nowrap'}}>{author.firstName}</td>
                <td>{author.lastName}</td>
                <td>{author.email}</td>
                <td>{author.publishingHousesIds.map(id => {
                    return <div key={id}>{id}</div>
                })}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/authors/" + author.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(author.id)}>Delete</Button>
                        <Button size="sm" color="warning" tag={Link} to={"/makeAgreement/" + author.id}>
                            Make agreement
                        </Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/authors/new">Add Author</Button>
                    </div>
                    <h3>All authors</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="5%">ID</th>
                            <th width="20%">First name</th>
                            <th width="20%">Last name</th>
                            <th width="20%">Email</th>
                            <th>Publishing houses IDs</th>
                            <th width="10%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {authorList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default AuthorList;