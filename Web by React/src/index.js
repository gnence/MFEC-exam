import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Table from './Table'
import * as serviceWorker from './serviceWorker';

ReactDOM.render(<Table />, document.getElementById('root'));

serviceWorker.unregister();
