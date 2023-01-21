import './App.css';
import { useEffect, useState } from 'react';

function App() {

  // message 초기값 설정 (""로 설정)
  const [message, setMessage] = useState("");

  // useEffect(함수, 배열) : 컴포넌트가 화면에 나타났을 때 자동 실행
  useEffect(() => {
    // fetch(url, options) : Http 요청 함수
    fetch("/test")
      .then(response => response.text())
      .then(message => {
        setMessage(message);
      });
  }, [])

  return (
    <div className="App">
      <header className="App-header">
        <h2> 취업 정보는 코코's 블로그에서 </h2>
        <h2> ↓↓↓ </h2>
        <h2> ↓↓↓ </h2>
        <a href={ message } target='_blank' rel='noreferrer' style={ {color:"yellow"} }>  <h2> 바로가기 [{ message }] </h2> </a>
      </header>
    </div>
  );
}

export default App;
