import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import React, { Component } from 'react'

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
            <img src={logo} className="App-logo" alt="logo" />
            <div className="App-intro">
            <h2>Tests:</h2>
                {tests.map(test =>
                    <div key={test.testName}>
                        {test.testName} ({test.status})
                    </div>
                    )}
            </div>
            </header>
            </div>
        );
    }
}
export default App;
