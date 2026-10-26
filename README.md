# Self Defence Academy Management System (SDAMS)

A Java desktop application built with Java Swing for managing the daily operations of a martial arts institution. This was developed as a group coursework project for the Object-Oriented Development with Java (OODJ) module.

## Overview

SDAMS replaces the manual, fragmented record-keeping at the Self Defence Academy with a centralized system for managing students, classes, training schedules, payments, instructors, facilities, and equipment. The application is built entirely with **Java** and **Java Swing** (GUI), follows **object-oriented programming principles**, and uses **text files** as its data storage mechanism (no databases).

## User Roles

The system supports **six** distinct user roles, each with its own dedicated menu and permissions:

| Role | Key Functionalities |
|------|-------------------|
| **Manager** | Manage administrators; staff reports (dashboard/chart/table with filtering); revenue reports (monthly/yearly) |
| **Administrator** | Manage staff (instructors & facilities officers); manage students; view payment records |
| **Student** | Book, view, search, reschedule & cancel classes; make payments; rate instructors |
| **Instructor** | Manage class schedules; issue facility problems; view instructor ratings |
| **Facilities Officer** | Manage equipment inventory; update facility problem status; equipment stock reports |
| **Attendance Officer** | Record class attendance (present/absent/late); view & search attendance records |

## Core Workflows

1. **Class Workflow:** Instructor creates class → Student books & pays → Manager sees revenue → Attendance Officer records attendance → Student rates instructor.
2. **Facility Workflow:** Instructor reports a facility problem → Facilities Officer updates it until resolved.

## Tech Stack

- **Language:** Java
- **GUI:** Java Swing
- **Storage:** Text files (.txt)
- **Paradigm:** Object-Oriented Programming (OOP)

## Key Features

- Role-based access control — each role sees only its own menu after login.
- Full CRUD operations across all modules (add, view, search, update, delete).
- Filterable and searchable reports (staff, revenue, stock, attendance).
- Input validation on all user entries to prevent logical errors.
- Additional features beyond the mandatory requirements (extra marks).

---

Feel free to trim this down if you want a shorter "About" blurb (GitHub only shows the first ~350 characters), but the top few paragraphs work well as the repo description and README intro.
