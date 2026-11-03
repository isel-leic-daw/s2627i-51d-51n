import {
  createContext,
  ReactElement,
  ReactNode,
  useContext,
  useState,
} from "react";

type Theme = "light" | "dark";

type ThemeContextType = {
  theme: Theme;
  setTheme: (newTheme: Theme) => void;
};

const ThemeContext = createContext<ThemeContextType>({
  theme: "dark",
  setTheme: () => {},
});

type ThemeProviderProps = {
  children: ReactNode;
};

function ThemeProvider(props: ThemeProviderProps): ReactElement {
  const [theme, setTheme] = useState<Theme>("dark");
  const context = {
    theme: theme,
    setTheme: setTheme,
  };
  return <ThemeContext value={context}>{props.children}</ThemeContext>;
}

function useTheme(): [theme: Theme, setTheme: (v: Theme) => void] {
  const context = useContext(ThemeContext);
  return [context.theme, context.setTheme];
}

function ThemePicker() {
  const [theme, setTheme] = useTheme();
  const darkEnable = theme != "dark";
  const lightEnable = theme != "light";
  return (
    <>
      {theme}
      <button disabled={!lightEnable} onClick={() => setTheme("light")}>
        light
      </button>
      <button disabled={!darkEnable} onClick={() => setTheme("dark")}>
        dark
      </button>
    </>
  );
}

function SomeComponent({ children }: { children: ReactNode }) {
  return <div>{children}</div>;
}

function AnotherComponent() {
    const [theme] = useTheme()
    return (
        <>
            Showing component according to {theme}
        </>
    )
}

export function App() {
  return (
    <ThemeProvider>
      <ThemePicker />
      <SomeComponent>
        <AnotherComponent />
      </SomeComponent>
    </ThemeProvider>
  );
}
