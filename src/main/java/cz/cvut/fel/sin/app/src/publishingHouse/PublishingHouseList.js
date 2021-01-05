import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import AppNavbar from '../main/AppNavbar';
import {Link} from 'react-router-dom';

class PublishingHouseList extends Component {

    constructor(props) {
        super(props);
        this.state = {houses: [], isLoading: true};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('publishingHouses')
            .then(response => response.json())
            .then(data => this.setState({houses: data, isLoading: false}));
    }

    async remove(id) {
        await fetch(`/publishingHouses/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedHouses = [...this.state.houses].filter(i => i.id !== id);
            this.setState({houses: updatedHouses});
        });
    }

    render() {
        const {houses, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const houseList = houses.map(house => {
            return <tr key={house.id}>
                <td>{house.id}</td>
                <td style={{whiteSpace: 'nowrap'}}>{house.name}</td>
                <td>{house.address}</td>
                <td>{house.booksIds.map(id => {
                    return <div key={id}>{id}</div>
                })}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/publishingHouses/" + house.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(house.id)}>Delete</Button>
                        <Button size="sm" color="warning" tag={Link} to={"/releaseBook/" + house.id}>
                            Release book
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
                        <Button color="success" tag={Link} to="/publishingHouses/new">Add Publishing House</Button>
                    </div>
                    <h3>All publishing houses</h3>
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
                        {houseList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default PublishingHouseList;