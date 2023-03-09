# Tech Stack
- Jakarta EE 8
- JSF 2.3
- PrimeFaces 8.0

# Run the Application
- Clean and Build `LibraryManagementSystem'
- Deploy `LibraryManagementSystem'
- Clean and Build `LibraryManagementSystem'
- Run `LibraryManagementSystem'
	- A web page with the url `http://localhost:8080/LibraryManagementSystem-war` should be displayed

# Additional Features
- Authentication Filter: Only permit authenticated user to access the pages
- Custom Validation Checks: Uses Regex to check NRIC (ensuring starts with 'S' or 'T', 7 numerical characters and 1 alphabet), Mobile Phone (ensuring starts with '8' or '9', and a total of 8 digits)
- Responsive Navigation Bar & Pages: Adjusted according to the window size of the screen
- Return All Books/Pay total fine: Allows the users to return all books/pay total fine in by pressing one button rather than returning the book/paying the fine by pressing the buttons one by one (this comes with the assumption that the user wants to return all books/pay all the fines at one go)

# Note
- For line 42 of createMember.xhtml, do not format the following code:
onkeypress="if (event.which &lt; 48 || event.which &gt; 57) return false;"> <!-- do not format this, as it allows user to not key in any alphabets -->
- "Pay a Total Fine of $.." allows the user to pay the total fine amount and returning all books