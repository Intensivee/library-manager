export class BorrowDetails {
    constructor(public copyId: number,
                public bookId: number,
                public bookTitle: string,
                public pages: number,
                public borrowDate: Date,
                public returnDate: Date){}
  }


