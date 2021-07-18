# Project Description
The purpose of this project is to practice deploying a web application using the MERN stack (MongoDB, Express, React, NodeJS) on Heroku.  It consists of an application which allows you to enter your assets and liabilities and display this full list for you along with the ability to edit and delete any existing assets and liabilities.

# Project Requirements
- [x] Form that inputs the following fields
    - [x] **Type**(string): can only contain "Asset" and "Liability"
    - [x] **Name**(string): can contain any string
    - [x] **Balance**(number): can contain all real numbers
- [x] Display a Table that contains the Assets/Liabilities added through the form. This table
will have a column for each field listed above.
- [x] Contents of this table must be persisted in a Database. When the page is refreshed, the
table should populate with the persisted data.
- [x] Provide a way to Delete an asset or liability from the table
- [x] Display the following calculations based on the Assets/Liabilities that exist in the table.
These calculations must be done on the server:
    -[x] **Net Worth**: Summed total of Assets and Liabilities in the table. This can be
positive or negative.
    -[x] **Assets Total**: Summed total of the assets in the table.
    -[x] **Liabilities Total**: Summed total of the liabilities in the table.
- [x] Deploy the application

# Reflection
This project was a challenge for me to learn to build a web application completely new from the ground up within 4 days.  During the initial design and research portion, I looked up what are the best tech stacks to use to create a web application and saw that the MERN and Heroku were among the top and decided to proceed with this process.  Learning both of these technologies completely new from the ground up, I was able to grasp the basics of how everything connected together.  However, I did run into a large number of difficulties and would need to learn best practices in how to handle these situations:

## Backend
* File structure seems messy, I've seen examples of people putting the server.js fil in the backend folder and other who put it at the top level.  What would be the best strategy in this file placement especially for easy deployment?

## Frontend
* Async programming feels very messy, especially if you need to do a large number of nested call backs.  Are there better ways to implement this inline rather than nested in each other's functions?

## Deployment
* package.json management feels messy especially when deploying, is there a good way to structure the file structure for frontend and backend code to make package deployment clean.  Right now, my workaround is to point the main package to a package in the backend folder to run the commands to run server.js.  This feels to unstructured

# Enhancements
- [ ] Add unit test to wealths.js
- [ ] Add unit test to all components
- [ ] Provide a way to refresh the graph along with the numerical values after delete
- [ ] Add the ability to include individual clients and change data according to the selected client
- [ ] Restructure the file structure to frontend and backend folders
- [ ] Setup baseURL to be more dynamic rather than manually entered
- [ ] Improve on the UI so it doesn't look as crappy
- [ ] Add further documentation to code
