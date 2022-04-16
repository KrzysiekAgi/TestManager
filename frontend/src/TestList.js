import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import './TestList.css'

class TestList extends Component {
    
    constructor(props) {
        super(props);
        this.state = {tests: [], isLoading: true};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('/api/tests')
        .then(response => response.json())
        .then(data => this.setState({tests: data, isLoading: false}));
    }

    async remove(id) {
        await fetch(`/api/tests/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedTests = [...this.state.tests].filter(i => i.id !== id);
            this.setState({tests: updatedTests});
        });
    }

    render () {
        const {tests, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const testsList = tests.map(test => {
            return <tr key={test.id}>
                <td style={{whiteSpace: 'nowrap'}}>{test.testName}</td>
                <td>{test.status}</td>
                <td>
                    <ButtonGroup>
                        <Button size='sm' color='primary' tag={Link} to={'/tests/' + test.id}>Edit</Button>
                        <Button size='sm' color='danger' onClick={() => this.remove(test.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div id="c1">
                        <Button color="success" tag={Link} to="/tests/new">Add test</Button>
                    </div>
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
export default TestList;