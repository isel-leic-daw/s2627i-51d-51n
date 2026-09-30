import { type Duration, type Instant, duration, durationBetween } from "./time";

// Define actions as discriminated union
export type Action =
  | { kind: "start"; timestamp: Instant }
  | { kind: "tick"; timestamp: Instant }
  | { kind: "lap"; timestamp: Instant }
  | { kind: "stop"; timestamp: Instant }
  | { kind: "reset" };

// Action creators
export function actionStart(timestamp: Instant): Action {
  return { kind: "start", timestamp };
}

export function actionTick(timestamp: Instant): Action {
  return { kind: "tick", timestamp };
}

export function actionLap(timestamp: Instant): Action {
  return { kind: "lap", timestamp };
}

export function actionStop(timestamp: Instant): Action {
  return { kind: "stop", timestamp };
}

export function actionReset(): Action {
  return { kind: "reset" };
}

// Define state
export type State =
  | { kind: "initial" }
  | {
      kind: "running";
      laps: Array<Duration>;
      startTimestamp: Instant;
      lastLapTimestamp: Instant;
      elapsed: Duration;
    }
  | { kind: "stopped"; laps: Array<Duration>; elapsed: Duration };

export const initialState: State = { kind: "initial" };

// Reducer
// - "pure function" (except for the console.log side-effect on an error)
// - E.g. does NOT observe the current timestamp
export function reduce(state: State, action: Action): State {
  switch (state.kind) {
    case "initial":
      switch (action.kind) {
        case "start":
          return {
            kind: "running",
            laps: [],
            startTimestamp: action.timestamp,
            lastLapTimestamp: action.timestamp,
            elapsed: duration(0),
          };
        case "tick":
          return unexpectedStateAndAction(state, action);
        case "lap":
          return unexpectedStateAndAction(state, action);
        case "stop":
          return unexpectedStateAndAction(state, action);
        case "reset":
          return unexpectedStateAndAction(state, action);
      }
    case "running":
      switch (action.kind) {
        case "start":
          return unexpectedStateAndAction(state, action);
        case "tick":
          return {
            ...state,
            elapsed: durationBetween(action.timestamp, state.startTimestamp),
          };
        case "lap": {
          const thisLap = durationBetween(
            action.timestamp,
            state.lastLapTimestamp,
          );
          return {
            ...state,
            lastLapTimestamp: action.timestamp,
            laps: [...state.laps, thisLap],
          };
        }
        case "stop": {
          const thisLap = durationBetween(
            action.timestamp,
            state.lastLapTimestamp,
          );
          return {
            kind: "stopped",
            elapsed: durationBetween(action.timestamp, state.startTimestamp),
            laps: [...state.laps, thisLap],
          };
        }
        case "reset":
          return unexpectedStateAndAction(state, action);
      }
    case "stopped":
      switch (action.kind) {
        case "start":
          return unexpectedStateAndAction(state, action);
        case "tick":
          return unexpectedStateAndAction(state, action);
        case "lap":
          return unexpectedStateAndAction(state, action);
        case "stop":
          return unexpectedStateAndAction(state, action);
        case "reset":
          return initialState;
      }
  }
}

function unexpectedStateAndAction(state: State, action: Action) {
  console.error("Unexpected state and action combination", state, action);
  return state;
}
