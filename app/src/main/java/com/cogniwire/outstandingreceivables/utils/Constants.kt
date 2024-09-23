package com.cogniwire.outstandingreceivables.utils

/*
These constants are used for managing intent logic.
*/

object Constants {
    const val INTENT_BORROWER = "borrower"
    const val INTENT_CURRENCY = "XXX"
    const val INTENT_AMOUNT = "000"
    const val INTENT_REASON = "reason"
    const val INTENT_DATE = "dd-MM-yyyy"

    const val EXISTS_ID = "exists"                                                                  //This constant will be
                                                                                                    //tested for existence in a NEW (null/doesn't exist)
                                                                                                    //vs. an EDIT (does exist/not null)

    const val REQUEST_CODE = 0                                                                      //Number values are arbitrary
    const val EDIT_REQUEST_CODE = 1                                                                 //they are assigned as result codes for intents

}