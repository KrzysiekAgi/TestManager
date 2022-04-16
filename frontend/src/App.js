import React, { Component } from 'react';
import { Outlet, Link } from "react-router-dom";
import './App.css';
import Home from './Home';
import TestList from './TestList';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

class App extends Component {
  render () {
    return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home} />
            <Route path='/tests' exact={true} component={TestList} />
          </Switch>
        </Router>
    );
  }
}
export default App;
