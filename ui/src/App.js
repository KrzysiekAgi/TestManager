import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import React, { Component } from 'react'
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import TestList from "./TestList";
import TestEdit from './TestEdit';
import { Button } from 'bootstrap';

class App extends Component  {
    state = {
        tests: []
    };

    async componentDidMount() {
        const response = await fetch('/tests');
        const body = await response.json();
        this.setState({tests: body});
      }

    render() {
        const {tests} = this.state;
        return (
            <div className="App">
            <header className="App-header">
            <div className="App-intro">
            <table>
                <tr>
                    <th>Test Name</th>
                    <th>Test Status</th>
                </tr>
            {tests.map((val, key) => {
                return (
                    <tr key={key}>
                        <td>{val.testName}</td>
                        <td>{val.status}</td>
                    </tr>
                )
            })}
            </table>
            <button type="button">
                Click Me
            </button>
            </div>
            </header>
            </div>
        );
    }
}
export default App;
