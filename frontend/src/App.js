import React, { Component } from 'react';
import './App.css';
import { Button, ButtonGroup, Container, FormGroup, Form } from 'reactstrap';
import {toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import TestStatus from './TestStatus';
import { isTestNameValid } from './utils';
import TestList from './TestList';

const headers = {
  'Accept': 'application/json',
  'Content-Type': 'application/json'
}

class App extends Component {

  constructor(props) {
    super(props);
    this.state = {tests: [], isLoading: true, newTestName: "", renamedTest: ""};
    this.remove = this.remove.bind(this);
    this.handleNewTestNameChange = this.handleNewTestNameChange.bind(this);
    this.addTest = this.addTest.bind(this);
    this.renameTest = this.renameTest.bind(this);
    this.handleTestRename = this.handleTestRename.bind(this);
  }

  testStatusCallbackFunction = () => {
      this.setState({tests: [...this.state.tests]})
  }

  componentDidMount() {
    this.setState({isLoading: true});
    fetch('/api/tests')
    .then(response => response.json())
    .then(data => this.setState({tests: data, isLoading: false}));
  }

  handleNewTestNameChange(event) {
    this.setState({newTestName: event.target.value});
  }

  async addTest(event) {
    event.preventDefault();
   if (isTestNameValid(this.state.newTestName, this.state.tests)) {
    await fetch('/api/tests/', {
        method: 'POST',
        body: this.state.newTestName,
        headers: headers
    })
    .then(response => response.json())
    .then(data => {
      let updatedTests = [...this.state.tests, data];
      this.setState({tests: updatedTests, newTestName: ""});
    });
   }
  }

  handleTestRename(event) {
      this.setState({renamedTest: event.target.value})
  }

  async renameTest(id) {
    if (isTestNameValid(this.state.renamedTest, this.state.tests)) {
        await fetch(`/api/tests/${id}/name`, {
            method: 'PUT',
            body: this.state.renamedTest,
            headers: headers
        }).then(() => {
            let updatedTests = [...this.state.tests]
                .filter(i => i.id === id)
                .map(i => i.testName = this.state.renamedTest)
                this.setState({updatedTests, renamedTest: ""});
        })
    } 
  }

  async remove(id) {
    await fetch(`/api/tests/${id}`, {
        method: 'DELETE',
        headers: headers
    }).then(() => {
        let updatedTests = [...this.state.tests].filter(i => i.id !== id);
        this.setState({tests: updatedTests});
    });
  }

  render () {
    const {tests, isLoading} = this.state;
    toast.configure();

    if (isLoading) {
        return <p>Loading...</p>;
    }

    const testsList = tests.map(test => {
        return <tr key={test.id}>
            <td style={{whiteSpace: 'nowrap'}}>{test.testName}</td>
            <td>{test.status}</td>
            <td>
                <TestStatus test={test} callback={this.testStatusCallbackFunction}/>
            </td>
            <td>
                <ButtonGroup>
                    <Button size='sm' color='warning' 
                        onClick={() => this.remove(test.id)}>Delete</Button>
                </ButtonGroup>
            </td>
            <td>
                <FormGroup id="c1">
                    <input onChange={this.handleTestRename.bind(this)} type="text" value={test.tempName} placeholder="New name"/>
                    <Button size='sm' color='secondary' 
                        onClick={() => this.renameTest(test.id)}>Rename Test
                    </Button>
                </FormGroup>
            </td>
        </tr>
    });

    return (
        <div>
            <Container fluid>
                <Form onSubmit={this.addTest}>
                    <FormGroup id="c1">
                        <input onChange={this.handleNewTestNameChange.bind(this)} type="text" value={this.state.newTestName} placeholder="Test name"/>
                        <button>Add Test</button>  
                    </FormGroup>
                </Form>
                <h3>Test Manager</h3>
                <TestList testsList={testsList} />
            </Container>
        </div>
    );
  }
}
export default App;
