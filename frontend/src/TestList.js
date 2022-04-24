import React, { Component } from 'react';
import { Table } from 'reactstrap';

class TestList extends Component {

    render() {
        return (
            <Table className='mt-4'>
                <thead>
                    <tr>
                        <th>Test Name</th>
                        <th>Test Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>{this.props.testsList}</tbody>
            </Table>
        )
    }
} export default TestList;