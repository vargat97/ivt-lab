# Test plans

- FiringMode is Single. Try to fire primary store, but it reports failure. Test, that ship not gonna try to fire the secondary.

-FiringMode is Single. Try to fire, but the store is empty. Test, that ship tries to fire the other one.

-Test that the first time the primary store is fired.

-Test the alternating. Fire with the primary, and test that if the secondary is not empty, the the store next in line is the secondary

-Firing Mode is ALL, test that if all stores report failure, the returning value is false.

