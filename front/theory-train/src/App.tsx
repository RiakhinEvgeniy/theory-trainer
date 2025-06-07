import './styles/user-style.css'
import './App.css'
import Header from './components/Header'
import ButtonTest from './components/buttons/Button'
import GettingNextPrevUser from './components/hooks/GettingNextPrevUser'
import QuizPage from './components/QuizPage'
import GettingQuestion from './components/hooks/GettingQuestion'



function App() {

  const { nextUser, prevUser, getUserById } = GettingNextPrevUser();
  const { nextQuestion, prevQuestion, questionById } = GettingQuestion();

  return (
    <>
      <Header userId={getUserById}></Header>
      <div>
        <ButtonTest buttonText='NEXTUSER' getUser={nextUser}></ButtonTest>
        <ButtonTest buttonText='PREVUSER' getUser={prevUser}></ButtonTest>
      </div>

      <QuizPage idQuestion={questionById} />

      <div>
        <ButtonTest buttonText='NEXTQUESTION' getUser={nextQuestion}></ButtonTest>
        <ButtonTest buttonText='PREVQUESTION' getUser={prevQuestion}></ButtonTest>
      </div>

    </>
  )
}

export default App
