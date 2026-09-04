# Test Strategy — Proton Mail Web Automation

## 1. Objective

Validate the core functional quality of Proton Mail Web across authentication,
message composition, drafts, inbox management, organization (folders/labels),
search, filters, attachments, and asynchronous workflows (undo-send,
scheduled send). The suite prioritizes regression value and long-term
maintainability over raw test count — 28 documented test cases, with 19
selected for automation.

## 2. Scope

**In scope**
- Authentication & session (login, invalid login, logout)
- Compose & send (recipient handling, CC/BCC, validation, two-account
  send/receive verification)
- Drafts (auto-save, discard, persistence across refresh)
- Inbox & message actions (read/unread, archive, delete/restore, permanent
  delete)
- Folders & labels (move message to folder, folder deletion behavior)
- Search (keyword search, no-results state)
- Filters (create/apply rule, enable/disable, edit)
- Attachments (upload and send, oversized-file rejection)
- Asynchronous workflows (Undo Send, Scheduled Send, edit before send)

**Out of scope**
- Native mobile apps (iOS/Android)
- Billing/subscription management flows
- Full account-security surface (2FA hardware key enrollment, recovery-phrase
  workflows) — high account-risk, low regression frequency; kept exploratory
- Deep encryption/PGP internals validation (functional UI behavior only, not
  cryptographic correctness)
- Load/performance testing

## 3. Assumptions

- Two disposable Proton Mail test accounts are available (Sender, Receiver)
  and neither holds real/personal data.
- Free-tier UI is the primary target; paid-tier-only features (if
  encountered) are documented but not blocked on.
- Test environment: Chrome (primary, via WebDriverManager), desktop viewport.
- Proton's UI is an evolving SPA with async state updates (no full page
  reloads for most actions) — synchronization relies on explicit Selenium
  waits, not fixed sleeps.
- No direct API/DB access is available for test-data seeding; setup relies on
  UI-driven fixtures.

## 4. Risk-Based Prioritization

| Priority | Definition | Examples in this app |
|---|---|---|
| P0 — Critical | Blocks a core user journey | Login success/failure, Send email, Undo Send |
| P1 — High | Significant functionality, high business/user impact | Drafts persistence, Search, Archive/Delete/Restore, Attachments, Scheduled Send |
| P2 — Medium | Useful coverage, lower immediate risk | Folder management, Filters, edit-before-send flows |
| P3 — Low | Edge/cosmetic/low-risk | Folder deletion edge case, filter-rule editing |

Risk drivers: (1) how many downstream workflows depend on this state being
correct, (2) how frequently the flow is used in real usage, (3) how likely
the flow is to break silently due to async/timing issues (Undo Send,
Scheduled Send, draft auto-save).

## 5. Test Levels

- **UI functional automation** — primary layer; drives the real browser to
  validate end-to-end user-visible behavior and underlying state (e.g., a
  message actually appears in Trash, not just that a toast appeared).
- **Manual/exploratory** — used for security-sensitive flows, low-frequency
  edge cases, and scenarios where the cost of automating exceeds the
  regression value returned.

## 6. Automation Approach

- **Framework:** Java + Selenium WebDriver 4.18 + TestNG 7.9, matching
  production-realistic tooling used across the candidate's professional
  experience.
- **Design pattern:** Page Object Model — a `BasePage` class centralizes
  reusable wait/click/type/upload helpers; each screen (`LoginPage`,
  `ComposePage`, `MailBoxPage`, `NavigationPage`) extends it with its own
  locators and actions.
- **Driver management:** `DriverFactory` manages a `ThreadLocal<WebDriver>`
  instance via WebDriverManager, supporting Chrome/Firefox/Edge and
  headless execution via config.
- **Synchronization:** Explicit `WebDriverWait` against observable state
  changes (element visibility/clickability) — no `Thread.sleep`. Async flows
  (Undo Send, Scheduled Send) are handled by waiting for the actual
  resulting state (e.g., undo control becoming stale, message appearing in
  Sent) with a bounded timeout rather than a fixed delay.
- **Assertions:** Validate actual application state (message present in a
  given folder, read/starred/archived state, filter action correctly
  applied) rather than only checking that a UI element is visible/clickable.
- **Test data:** Externalized via `config.properties` (`ConfigReader`) —
  credentials, target URL, browser, and timeouts are not hardcoded in test
  classes. Repeated negative-input cases (e.g., malformed recipient formats)
  are strong candidates for a TestNG `@DataProvider` to avoid duplicating
  test methods per input.
- **Reporting:** ExtentReports (Spark HTML reporter) driven by a TestNG
  `ITestListener`, capturing pass/fail status and a screenshot on every
  failure; Log4j2 for structured console/file logging.
- **Test isolation:** Each test class logs in once per class rather than
  per method, reducing redundant real logins against the live application
  and lowering the risk of provider-side rate limiting during a full suite
  run.

## 7. What Will NOT Be Automated (and why)

| Scenario | Reason |
|---|---|
| Full account recovery / 2FA hardware key enrollment | High account-risk, low regression frequency, best validated manually |
| Permanently deleting a message from Trash | Destructive/irreversible action; safer as an exploratory check than a repeatable automated step |
| Recipient auto-suggest from contacts | Low functional risk, UI-suggestion behavior is better judged visually |
| Draft persistence across a hard page refresh | Timing-sensitive around browser reload; higher automation cost than regression value at this stage |
| Folder deletion containing messages | Edge case with undocumented exact behavior; needs human judgment on first pass before locking in an automated assertion |
| Disabling/re-enabling a filter rule | Multi-step timing-dependent flow with lower usage frequency than filter creation itself |
| Editing a scheduled message before it fires | Low-frequency edge interaction; higher automation cost relative to its regression value |
| Editing an existing filter rule | Low-value, redundant with filter-creation coverage already automated; not worth a dedicated automated case |
| Oversized attachment rejection | Depends on exact provider size-limit messaging, which is more efficiently verified by hand than encoded as a brittle assertion |
| Password-protected email send/access | Security/privacy-sensitive workflow; validated manually where the environment safely allows it |

## 8. Coverage Rationale

The 19 automated scenarios were chosen because they sit on the critical path
of "a user can reliably log in, send, receive, organize, and find their
email," have deterministic and verifiable end states (not just a transient
toast/animation), and are exercised on effectively every release — making
them high-value regression guards. Async flows (Undo Send, Scheduled Send)
are explicitly included because they are the area most likely to hide
regressions: timing-dependent bugs rarely surface in manual smoke testing
but are exactly what a correctly synchronized automated suite is positioned
to catch reliably.

## 9. Test Case Summary

| Category | Total Cases | Automated | Manual/Exploratory | Not Worth Automating |
|---|---|---|---|---|
| Authentication | 3 | 3 | 0 | 0 |
| Compose & Send | 5 | 4 | 1 | 0 |
| Drafts | 3 | 2 | 1 | 0 |
| Inbox & Message Actions | 4 | 3 | 1 | 0 |
| Folders & Labels | 2 | 1 | 1 | 0 |
| Search | 2 | 2 | 0 | 0 |
| Filters | 3 | 1 | 1 | 1 |
| Async Workflows | 3 | 2 | 1 | 0 |
| Attachments | 2 | 1 | 1 | 0 |
| Security / Session | 1 | 0 | 1 | 0 |
| **Total** | **28** | **19** | **8** | **1** |

Every required category from the assessment brief meets or exceeds its
stated minimum automated-scenario count.

## 10. Environment & Execution

- Local execution: Maven + TestNG via `mvn test`, using `testng.xml` as the
  suite definition.
- Config-driven: browser, headless mode, target URL, and credentials are
  read from `config.properties` (not hardcoded), with production secrets
  intended to move to environment variables before any public push.
- Reporting: `reports/ExtentReport.html` generated per run; failure
  screenshots embedded automatically via the TestNG listener.

## 11. Exit Criteria for This Assessment

- 19 automated scenarios implemented across all required categories.
- Each required category meets or exceeds the assessment's minimum
  automated-scenario count.
- At least one negative case for authentication and for compose/send.
- At least one asynchronous/timing-dependent scenario automated with proper
  synchronization (no arbitrary sleep).
- Test report and failure evidence (screenshots) generated on each run.
- README allows another engineer to clone, configure, and run the suite
  without hand-holding.
