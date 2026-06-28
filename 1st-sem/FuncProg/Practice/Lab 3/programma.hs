doubleMe a = a*2
doubleUs a b = doubleMe a + doubleMe b
doubleSmallNumber x = if x >= 100
                        then x
                        else doubleMe

jalol = "Sommakim jo'rala"
jalol x = doubleMe x