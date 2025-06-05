import './styles/user-style.css'
import './App.css'
import Header from './components/Header'
import ButtonTest from './components/buttons/Button'
import Question from './components/Question'
import Answer from './components/Answer'
import GettingNextPrevUser from './components/hooks/GettingNextPrevUser'



function App() {

  const { nextUser, prevUser, getUserById } = GettingNextPrevUser();

  return (
    <>
      <Header userId={getUserById}></Header>
      <Question questionText="Which company developed Java?"></Question>
      <section>
        <Answer id="1"></Answer>
        <Answer id="2"></Answer>
        <Answer id="3"></Answer>
      </section>

      <div>
        <ButtonTest buttonText='NEXTUSER' getUser={nextUser}></ButtonTest>
        <ButtonTest buttonText='PREVUSER' getUser={prevUser}></ButtonTest>
      </div>
    </>
  )
}

export default App
