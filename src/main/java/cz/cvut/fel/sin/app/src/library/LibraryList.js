import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import AppNavbar from '../main/AppNavbar';
import {Link} from 'react-router-dom';

class LibraryList extends Component {

    constructor(props) {
        super(props);
        this.state = {libraries: [], isLoading: true};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('libraries')
            .then(response => response.json())
            .then(data => this.setState({libraries: data, isLoading: false}));
    }

    async remove(id) {
        await fetch(`/libraries/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedLibraries = [...this.state.libraries].filter(i => i.id !== id);
            this.setState({libraries: updatedLibraries});
        });
    }

    render() {
        const {libraries, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const librariesList = libraries.map(library => {
            return <tr key={library.id}>
                <td>{library.id}</td>
                <td style={{whiteSpace: 'nowrap'}}>{library.name}</td>
                <td>{library.address}</td>
                <td>{library.booksIds.map(id => {
                    return <div key={id}>{id}</div>
                })}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/libraries/" + library.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(library.id)}>Delete</Button>
                        <Button size="sm" color="warning" tag={Link} to={"/addBook/" + library.id}>Add book</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/libraries/new">Add Library</Button>
                    </div>
                    <h3>All libraries</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="5%">ID</th>
                            <th width="20%">Name</th>
                            <th width="20%">Address</th>
                            <th>Books IDs</th>
                            <th width="10%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {librariesList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default LibraryList;