import React, {Component} from 'react';
import './App.css';
import Home from './Home';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import BookList from '../book/BookList';
import BookEdit from '../book/BookEdit';
import LibraryList from '../library/LibraryList';
import LibraryEdit from '../library/LibraryEdit';
import AuthorList from '../author/AuthorList';
import AuthorEdit from "../author/AuthorEdit";
import PublishingHouseList from "../publishingHouse/PublishingHouseList";
import PublishingHouseEdit from "../publishingHouse/PublishingHouseEdit";
import AddBook from "../service/AddBook";
import MakeAgreement from "../service/MakeAgreement";
import ReleaseBook from "../service/ReleaseBook";

class App extends Component {
  render() {
    return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home}/>
            <Route path='/books' exact={true} component={BookList}/>
            <Route path='/libraries' exact={true} component={LibraryList}/>
            <Route path='/authors' exact={true} component={AuthorList}/>
            <Route path='/publishingHouses' exact={true} component={PublishingHouseList}/>
            <Route path='/books/:id' component={BookEdit}/>
            <Route path='/libraries/:id' component={LibraryEdit}/>
            <Route path='/authors/:id' component={AuthorEdit}/>
            <Route path='/publishingHouses/:id' component={PublishingHouseEdit}/>
            <Route path='/addBook/:id' component={AddBook}/>
            <Route path='/makeAgreement/:id' component={MakeAgreement}/>
            <Route path='/releaseBook/:id' component={ReleaseBook}/>
          </Switch>
        </Router>
    )
  }
}

export default App;