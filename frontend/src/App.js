
import React, {useState, useEffect} from 'react';
import logo from './logo.svg';
import './App.css';

function App() {
  const [message, setMessage] = useState("");
  useEffect(() => {
    fetch('/api/getNow')
      .then(response => response.text())
      .then(message => {
        setMessage(message);
      });
  }, [])
  return ( 
  <div className = "App" >
    <header className = "App-header" >
    <img src = {logo }  className = "App-logo"  alt = "logo" / >
    <h1>안녕하세요</h1>
    <h2 className = "App-title" > {
      message
    } </h2> 
    </header> <p className = "App-intro" >
    </p> 
  </div>
  )
}

export default App;
