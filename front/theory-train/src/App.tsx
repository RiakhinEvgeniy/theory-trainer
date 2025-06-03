import { useState } from 'react'
import './styles/user-style.css'
import './App.css'
import Header from './components/Header'
import ButtonTest from './components/Button'

function App() {
  const [nextUser, setNextUser] = useState<number>(3);

  function getNextUser() {
    setNextUser(() => nextUser + 1);
    console.log(nextUser);
    return nextUser;
  }

  function getPreviousUser() {
    setNextUser(() => nextUser - 1);
    console.log(nextUser);
    return nextUser;
  }

  return (
    <>
      <Header userId={nextUser}></Header>
      <div>
        <ButtonTest buttonText='NEXT' getNextUser={getNextUser}></ButtonTest>
        <ButtonTest buttonText='PREV' getNextUser={getPreviousUser}></ButtonTest>
      </div>
    </>
  )
}

export default App
