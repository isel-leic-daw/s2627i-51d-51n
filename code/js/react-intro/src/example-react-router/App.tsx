import { Children, useEffect, useEffectEvent, useRef, useState } from "react";
import {
  createBrowserRouter,
  Link,
  Outlet,
  RouterProvider,
  useLocation,
  useNavigate,
  useParams,
} from "react-router";

// An array with all the routes
const routes = [
  // a route
  {
    path: "/some/path",
    // the *element* to use for this route
    element: <SomeComponent />,
  },

  // another route
  {
    path: "/another/path",
    // the *component* to use for this route (notice the uppercase "C")
    Component: AnotherComponent,
  },

  // using path templates (dynamic segments)
  {
    path: "/repos/:id",
    Component: RepoComponent,
  },

  // a route at the root path
  {
    path: "/",
    element: <RootComponent />,
  },

  {
    path: "main",
    Component: MainComponent,
    children: [
      {index: true, Component: HomeComponent},
      {path: "child1", Component: Child1Component },
      {path: "child2", Component: Child2Component },
    ]
  },

  // a "splat" route (notice the "*") that matchs
  {
    path: "/*",
    element: <Fallback />,
  },
];

function SomeComponent() {
  return (
    <>
      <h1>Some Component</h1>
      <a href="/another/path"> plain "a" link to /another/path</a>
    </>
  );
}

function AnotherComponent() {
  return (
    <>
      <h1>Another Component</h1>
      <Link to="/some/path">React Router Link to /some/path</Link>
    </>
  );
}

function RepoComponent() {
  const { id } = useParams();
  return (
    <>
      <h1>Repository {id}</h1>
    </>
  );
}

function RootComponent() {
  return (
    <>
      <ul>
        <li>
          A plain <code>a</code> link to{" "}
          <a href="/some/path">
            <code>/some/path</code>
          </a>
        </li>
        <li>
          A plain <code>a</code> link to{" "}
          <a href="/another/path">
            <code>/another/path</code>
          </a>
        </li>
        <li>
          A <code>Link</code> to{" "}
          <Link to="/some/path">
            <code>/some/path</code>
          </Link>
        </li>
        <li>
          A <code>Link</code> to{" "}
          <Link to="/another/path">
            <code>/another/path</code>
          </Link>
        </li>
      </ul>
    </>
  );
}

const COUNT_DOWN_IN_SECS = 4;

function Fallback() {
  const [countDown, setCountDown] = useState(COUNT_DOWN_IN_SECS);
  const navigate = useNavigate();
  const location = useLocation();
  const timerCallback = useEffectEvent(() => {
    if (countDown == 0) {
      // nothing to do
      return;
    }
    if (countDown == 1) {
      navigate("/", { replace: true });
    }
    setCountDown(countDown - 1);
  });
  useEffect(() => {
    const iid = setInterval(() => timerCallback(), 1000);
    return () => {
      clearInterval(iid);
    };
  }, []);
  return (
    <>
      <p>Path {location.pathname} is unknown</p>
      <p>Will be redirected to the app start in {countDown} seconds...</p>
    </>
  );
}

function MainComponent() {
  return (
    <>
    <h1>Main Component</h1>
    <ul>
      <li><Link to="/main/child1">Child 1</Link></li>
      <li><Link to="/main/child2">Child 2</Link></li>
    </ul>
    <Outlet />
    </>
  )
}

function HomeComponent() {
  return <h2>Home</h2>
}

function Child1Component() {
  return <h2>Child 1</h2>
}

function Child2Component() {
  return <h2>Child 2</h2>
}

const router = createBrowserRouter(routes);

export function App() {
  return <RouterProvider router={router} />;
}
