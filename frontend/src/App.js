import React, { Component } from 'react';
import './App.css';
import { Button, ButtonGroup, Container, FormGroup, Form, Input, Label, Table } from 'reactstrap';

const headers = {
  'Accept': 'application/json',
  'Content-Type': 'application/json'
}

class App extends Component {
  
  constructor(props) {
    super(props);
    this.state = {tests: [], isLoading: true, newTestName: ""};
    this.remove = this.remove.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});
    fetch('/api/tests')
    .then(response => response.json())
    .then(data => this.setState({tests: data, isLoading: false}));
  }

  handleChange(event) {
    this.setState({newTestName: event.target.value});
  }

  async handleSubmit(event) {
    event.preventDefault();
    await fetch('/api/tests/', {
        method: 'POST',
        body: this.state.newTestName,
        headers: headers
    })
    .then(response => response.json())
    .then(data => {
      let updatedTests = [...this.state.tests, data];
      this.setState({tests: updatedTests});
    });
  
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

  async markGreen(id) {
    await fetch(`/api/tests/${id}/status`, {
        method: 'PUT',
        body: "PASSING",
        headers: headers
    }).then(() => {
        let updatedTests = [...this.state.tests]
            .filter(i => i.id === id)
            .map(i => i.status = "PASSING")
        this.setState({updatedTests});
    });
  }

  async markRed(id) {
    await fetch(`/api/tests/${id}/status`, {
        method: 'PUT',
        body: "FAILING",
        headers: headers
    }).then(() => {
        let updatedTests = [...this.state.tests]
            .filter(i => i.id === id)
            .map(i => i.status = "FAILING")
        this.setState({updatedTests});
    });
  }

  render () {
    const {tests, isLoading, newTestName} = this.state;

    if (isLoading) {
        return <p>Loading...</p>;
    }

    const testsList = tests.map(test => {
        return <tr key={test.id}>
            <td style={{whiteSpace: 'nowrap'}}>{test.testName}</td>
            <td>{test.status}</td>
            <td>
                <ButtonGroup>
                    <Button size='sm' color='success' 
                        onClick={() => this.markGreen(test.id)}>Pass</Button>
                    <Button size='sm' color='danger' 
                        onClick={() => this.markRed(test.id)}>Fail</Button>
                </ButtonGroup>
            </td>
            <td>
                <ButtonGroup>
                    <Button size='sm' color='warning' 
                        onClick={() => this.remove(test.id)}>Delete</Button>
                </ButtonGroup>
            </td>
        </tr>
    });

    return (
        <div>
            <Container fluid>
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup id="c1">
                        <input onChange={this.handleChange.bind(this)} type="text" value={this.state.newTestName} placeholder="Test name"/>
                        <button>Add Test</button>  
                    </FormGroup>
                </Form>
                <h3>Test Manager</h3>
                <Table className='mt-4'>
                    <thead>
                        <tr>
                            <th>Test Name</th>
                            <th>Test Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>{testsList}</tbody>
                </Table>
            </Container>
        </div>
    );
  }
}
export default App;
