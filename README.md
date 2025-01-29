# DoneZo!

## Project Description
DoneZo! is a revolutionary task management solution tailored specifically to address the unique challenges faced by students. Designed to simplify and enhance the way students organize their responsibilities, DoneZo! empowers users to create, prioritize, and track tasks with ease. By consolidating academic, extracurricular, and personal obligations into a single, intuitive platform, the app reduces cognitive overload and helps students allocate their time efficiently. DoneZo! supports features like task prioritization, time-blocking, and customizable schedules, ensuring that every user can adapt the platform to their unique needs and preferences.

## Table of Contents
- [Project Description](#project-description)
- [Features](#features)
  - [Create Task](#create-task)
  - [Edit Task](#edit-task)
  - [Prioritization Sorting](#prioritization-sorting)
  - [Dashboard (Home Page)](#dashboard-home-page)
  - [Work Time Customization](#work-time-customization)
- [Credits](#credits)
- [License](#license)

## Features

### 1. Task List (Create Task)
**Fields:**
- **Task Name:** Brief title of the task.
- **Task Description (Optional):** Details or notes about the task.
- **Due Date:** Select via a user-friendly calendar picker (like Google Forms).
- **Task Status:** Dropdown with options:
  - Not Yet Started
  - In Progress
  - Completed
- **Difficulty & Time Estimation:**
  - Easy (≤ 1 hour)
  - Medium (1-4 hours)
  - Hard (5+ hours, or multi-day projects)
  - Users provide an estimated time to complete the task.
- **Timing Suggestions:** Based on difficulty and due date proximity, tasks are tagged:
  - "Do Immediately" (Urgent, due soon)
  - "Start Early" (Medium priority, due in a few days)
  - "Schedule Later" (Low priority, due in a week or more)

### 2. Task List (Edit Task and Delete Task)
Allows modification of:
- Task name, description, due date, status, difficulty, and estimated time.
- Automatically updates prioritization and timing suggestions when edits are made.

### 3. Suggested Workflow (Prioritization Sorting)
**Smart Workflow Suggestion Algorithm:**
Ranks tasks based on:
- **Due Date:** Nearer deadlines get higher priority.
- **Task Difficulty:** Harder tasks with long estimated times are pushed earlier.
- **Estimated Time Left:** Tasks are grouped to fit into available time slots.

**Suggested Workflow View:**
Dynamically reorders tasks into a streamlined, effective sequence.

### 4. Dashboard (Home Page)
- **Task Summaries:**
  - **Tasks Left:** Total number of pending tasks.
  - **Hours Remaining:** Cumulative estimate of hours to finish all tasks.
- **Difficulty Breakdown:**
  - Tasks tagged as "Do Immediately," "Start Early," or "Schedule Later."
- **Completion Progress:** Tracks how many tasks were completed recently.

### 5. Settings
**Feature Name: "Flexible Work Hours"**

**Purpose:**
Allows users to input or modify their available working hours, ensuring the suggested workflow view avoids conflicts with non-working times (e.g., classes, meetings, or personal commitments). The workflow dynamically rearranges tasks to fit within available time slots.

**How It Works:**
- **Define Work Hours:**
  - Users can set their daily work schedule (e.g., 9 AM - 12 PM, 2 PM - 5 PM).
  - Option to exclude specific times for commitments (e.g., classes, meetings).
- **Dynamic Task Rearrangement:**
  - Smart Workflow Suggestion Algorithm automatically rearranges tasks to fit within available work hours.
  - Tasks are redistributed to avoid conflicts and maintain productivity.
- **Visual Schedule Integration:**
  - Displays a daily/weekly calendar showing:
    - Allocated work hours.
    - Non-working time blocks (e.g., shaded sections for classes/meetings).
    - Task placements within the available slots.

## Credits
Developed by TEAM OOPSIES
Members:
BORONDIA, Kurt Ashley T.
CANSINO, Florence Lee F.
CO, Kyle Desmond P.
GUECO, Jasper King S.
LAPATAN, Jesus V.
RITO, Thaddeus Simon O.
TAGURA, Red Gabriel G.


## License
This project is licensed under the [MIT License](LICENSE).

