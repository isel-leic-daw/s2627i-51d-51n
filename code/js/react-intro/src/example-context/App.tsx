import { createContext, memo, ReactNode, useContext, useState } from "react";

type ExampleContextType = {
  value: number;
  incr: () => void;
};
const ExampleContext = createContext<ExampleContextType>({
  value: 0,
  incr: () => {},
});

type ExampleContextProviderProps = {
  children: ReactNode;
};

function ExampleContextProvider(props: ExampleContextProviderProps) {
  const [observedValue, setValue] = useState(0);
  const contextValue = {
    value: observedValue,
    incr: () => setValue((curr) => curr + 1),
  };
  return <ExampleContext value={contextValue}>{props.children}</ExampleContext>;
}

function useExampleContextValue() {
  const context = useContext(ExampleContext);
  return context.value;
}

function useExampleContextIncr() {
  const context = useContext(ExampleContext);
  return context.incr;
}

function GranParent() {
  const incr = useExampleContextIncr();
  console.log("Render GranParent");
  return (
    <div>
      <h1>GranParent</h1>
      <button onClick={() => incr()}>incr</button>
      <Parent />
    </div>
  );
}

const Parent = memo(function Parent() {
  console.log("Render Parent");
  return (
    <>
      <h2>Parent</h2>
      <Child />
    </>
  );
});

function Child() {
  const value = useExampleContextValue();
  console.log("Child")
  return (
    <>
      <h3>Child</h3>
      {value}
    </>
  );
}

export function App() {
  return (
    <ExampleContextProvider>
      <GranParent />
    </ExampleContextProvider>
  );
}
