import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {
  state = {
    isLoading: true,
    tests: []
  };

  async componentDidMount() {
    const response = await fetch('/api/tests');
    const body = await response.json();
    this.setState({tests: body, isLoading: false});
  }

  render () {
    const {tests, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>
    }

    return (
      <div className='App'>
        <header className='App-header'>
        <img src={logo} className="App-logo" alt="logo" />
        <div className='App-intro'>
          <h2>Test Manager</h2>
          {tests.map(test => 
            <div key={test.id}>
              {test.testName}
            </div>
            )}
        </div>
        </header>
      </div>
    );
  }
}
export default App;
