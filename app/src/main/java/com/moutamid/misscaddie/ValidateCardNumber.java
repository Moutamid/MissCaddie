package com.moutamid.misscaddie;

public class ValidateCardNumber {

    public static boolean isValid(String cardNumber){
        int sum = 0 ;
        boolean even = false;
        String  cardType = null;

        int firstDigit = getDigit(cardNumber,1);
        switch (firstDigit){
            case 4:
                cardType = "Visa";
                break;
            case 3:
                if (getDigit(cardNumber,2)==7) {
                    cardType= "American Express";
                } else {
                    return false;
                }
            case 5:
                cardType = "Master Card";
                break;
            case 6:
                cardType = "Discover";
                break;
        } //end card type switch

        int secondDigit = getDigit(cardNumber, 2);

        //loops through digits in reverse order right to left
        for (int i = cardNumber.length();i>0; i--){
            int digit = getDigit(cardNumber, i);

            //double every other digit
            if (even)
                digit += digit;

            even = ! even;

            //if result is greater than 9 then subtract 9
            if (digit > 9)
                digit = digit - 9;

            sum += digit;
        } //end of loop

        if (sum % 10 == 0)
        {
            System.out.println(cardNumber+" is valid");
            System.out.println("Card Type: "+cardType);
            return true;
        }
        else {
            System.out.println(cardNumber+ " is invalid");
            System.out.println("Card Type: Invalid");
            return false;
        }
    } //end of isValid class

    //gets digit at specified position
    private static int getDigit(String digitString, int position){
        String characterAtPosition = digitString.substring(position - 1, position);
        return Integer.parseInt(characterAtPosition);
    }

}
