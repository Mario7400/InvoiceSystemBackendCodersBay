
Strategy Pattern:
The code employs the Strategy Pattern to generate various sections of the PDF document.
Each PdfSection class implements the PdfSection interface with the addSection(Document document) method to add specific content.

Factory Pattern:
Additionally, it utilizes the Factory Pattern to dynamically create instances of PdfSection classes.
The PdfSectionFactory selects the appropriate PdfSection based on specific parameters or conditions,
enhancing the flexibility and extensibility of the system.

