import './App.css';
import { useEffect, useState } from 'react';

function App() {

  // message 초기값 설정 (""로 설정)
  const [result, setResult] = useState([]);

  // useEffect(함수, 배열) : 컴포넌트가 화면에 나타났을 때 자동 실행
  useEffect(() => {
    // fetch(url, options) : Http 요청 함수
    fetch("/nCrawling")
      .then(res => res.json())
      .then(res => {
        console.log(1, res);
        setResult(res);
      });
  }, [])

  const listItem = result.map(result => <li style={{ fontSize: "12px"}}> { result.title } -  [{ result.writing } : { result.agoTime }] </li> );

  return (
    <div className="App">
      <header className="App-header">
        <h4> 실시간 뉴스 크롤링 </h4>
        <div style={{textAlign: "left"}}>
            { listItem }
        </div>
      </header>
    </div>
  );
}

export default App;
