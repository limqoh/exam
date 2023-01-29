import './App.css';
import { useEffect, useState } from 'react';

function App() {

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

  const [analyze, setAnalyze] = useState([]);

  // useEffect(함수, 배열) : 컴포넌트가 화면에 나타났을 때 자동 실행
  useEffect(() => {
    // fetch(url, options) : Http 요청 함수
    fetch("/nAnalyzeText")
      .then(res => res.json())
      .then(res => {
        console.log(1, res);
        setAnalyze(res);
      });
  }, [])
  
  const listItem = result.map(result => <li style={{ fontSize: "12px", listStyle: "circle"}}> { result.title } <span style={{display: "block"}}> 　[{ result.writing } : { result.inputTime }] </span> </li> );
  const analyzeItem = analyze.map(analyze => <span style={{ fontSize: (analyze.count/3)+"px" }}>{ analyze.keyword }<span style={{ fontSize: "10px" }}>　</span></span> );

  return (
    <div className="App">
      <header className="App-header">
        <h4> 실시간 뉴스 크롤링 </h4>
        <div style={{textAlign: "left"}}>
            {/* { listItem } */}
        </div>
        <div>
            [뉴스 내용의 단어 빈도수]
            <div style={{wordBreak: "keep-all"}}>
                { analyzeItem }
            </div>
        </div>
      </header>
    </div>
  );
}

export default App;
