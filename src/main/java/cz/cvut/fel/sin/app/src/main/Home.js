import React, {Component} from 'react';
import './App.css';
import AppNavbar from './AppNavbar';
import {Link} from 'react-router-dom';
import {Button, Container} from 'reactstrap';

class Home extends Component {
    render() {
        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <Button color="link"><Link to="/books">Manage books</Link></Button>
                    <Button color="link"><Link to="/libraries">Manage libraries</Link></Button>
                    <Button color="link"><Link to="/authors">Manage authors</Link></Button>
                    <Button color="link"><Link to="/publishingHouses">Manage publishing houses</Link></Button>
                </Container>
            </div>
        );
    }
}

export default Home;