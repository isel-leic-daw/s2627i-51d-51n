import React, { useEffect, useReducer, useRef } from "react";
import {
  type Action,
  type State,
  actionLap,
  actionReset,
  actionStart,
  actionStop,
  actionTick,
  initialState,
  reduce,
} from "./reducer";
import { duration, formatDuration, now } from "./time";

type LapTimerProps = {
  label: string;
};

export function LapTimer(props: LapTimerProps) {
  const [state, dispatch] = useReducer(reduce, initialState);
  const intervalIdHolder = useRef<number | null>(null);
  useEffect(() => {
    return () => {
      // oxlint-disable-next-line react-hooks/exhaustive-deps
      const intervalId = intervalIdHolder.current;
      if (intervalId) {
        clearInterval(intervalId);
      }
    };
  }, []);
  const value = timerValueFromState(state);
  return (
    <>
      <p>{props.label}</p>
      <p>{value}</p>
      {buttons(state, dispatch, intervalIdHolder)}
      {laps(state)}
    </>
  );
}

export function buttons(
  state: State,
  dispatch: React.ActionDispatch<[action: Action]>,
  intervalIdHolder: React.RefObject<number | null>,
) {
  switch (state.kind) {
    case "initial":
      return (
        <button onClick={() => startTimer(dispatch, intervalIdHolder)}>
          start
        </button>
      );
    case "running":
      return (
        <>
          <button onClick={() => dispatch(actionLap(now()))}>lap</button>
          <button onClick={() => stopTimer(dispatch, intervalIdHolder)}>
            stop
          </button>
        </>
      );
    case "stopped":
      return (
        <>
          <button onClick={() => dispatch(actionReset())}>reset</button>
        </>
      );
  }
}

function startTimer(
  dispatch: React.ActionDispatch<[action: Action]>,
  intervalIdHolder: React.RefObject<number | null>,
) {
  dispatch(actionStart(now()));
  intervalIdHolder.current = setInterval(() => {
    dispatch(actionTick(now()));
  }, 10);
}

function stopTimer(
  dispatch: React.ActionDispatch<[action: Action]>,
  intervalIdHolder: React.RefObject<number | null>,
) {
  const iid = intervalIdHolder.current;
  if (iid != null) {
    clearInterval(iid);
    intervalIdHolder.current = null;
  }
  dispatch(actionStop(now()));
}

export function laps(state: State) {
  switch (state.kind) {
    case "initial":
      return null;
    case "running":
    case "stopped":
      return (
        <ul>
          {state.laps.map((lap, ix) => (
            <li key={ix}>
              {`Lap ${ix + 1}`} = {formatDuration(lap)}
            </li>
          ))}
        </ul>
      );
  }
}

function timerValueFromState(state: State): string {
  switch (state.kind) {
    case "initial":
      return formatDuration(duration(0));
    case "running":
    case "stopped":
      return formatDuration(state.elapsed);
  }
}
