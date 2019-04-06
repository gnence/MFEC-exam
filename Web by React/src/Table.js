import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

function readJSON(){
  var data = require('./data-cost.json'); //Pleass enter directory your log file
  return data;
}

const styles = theme => ({
  root: {
    width: '90%',
    marginTop: theme.spacing.unit * 5,
    margin: theme.spacing.unit * 5,
    overflowX: 'auto',
  },
  table: {
    minWidth: 700,
    padding : theme.spacing.unit,
  },
});

function createDataCost(id, moblie_number, promotion_name, cost){
  return {id, moblie_number, promotion_name, cost};
}

function createRowData(json_data){
  var rowData = [];
  for (let i in json_data.total_cost) {
    rowData.push(createDataCost(i+1, json_data.total_cost[i].mobile_no, json_data.total_cost[i].promotion_name, json_data.total_cost[i].cost));
  }
  return rowData;
}

const rows = createRowData(readJSON());

function SimpleTable(props) {
  const { classes } = props;

  return (
    <Paper className={classes.root}>
      <Table className={classes.table}>
        <TableHead>
          <TableRow>
            <TableCell>Moblie Number</TableCell>
            <TableCell align="right">Promotion</TableCell>
            <TableCell align="right">Cost (bath)</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map(row => (
            <TableRow key={row.id}>
              <TableCell component="th" scope="row">
              {row.moblie_number}
              </TableCell>
              <TableCell align="right">{row.promotion_name}</TableCell>
              <TableCell align="right">{row.cost}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Paper>
  );
}

SimpleTable.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SimpleTable);