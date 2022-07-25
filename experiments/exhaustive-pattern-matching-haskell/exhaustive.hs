import Text.Printf (printf)
-- The function is defined as an array of floats that returns the mean, so far, nothing special.
getMean :: [Float] -> Float
{-We are forced to define the function for all the domain (possible states of the input), in this case, all the float arrays.
  The functional way of addressing it is defining, in a general way, all the state space of the domain, in this case, the ZOM for
  ZOMBIES. This will accidentally cover the exceptional case of the collection being empty (dividing by zero) and the edge case
  of having a single element (division not needed)-}
getMean [] = 0
getMean [a] = a
getMean arr = sum arr / fromInteger (toInteger (length arr)) -- I'm quite sure there's a cleaner way to do this casting bit.


main :: IO ()
main = 
  let arr = [10, 20, 30] in
  let mean = getMean arr in
  putStrLn (printf "The mean of %s is %.2f" (show arr) mean)

-- If we were to remove the last definition of the function, we would get an incomplete pattern matching.
-- BUT, we do need to compile with the -Wall and -Werror flags to have this be a compilation error. I was
-- convinced that it was enabled by default for some reason.
{-
exhaustive.hs:7:1: error: [-Wincomplete-patterns, -Werror=incomplete-patterns]
    Pattern match(es) are non-exhaustive
    In an equation for ‘getMean’:
        Patterns of type ‘[Float]’ not matched: (_:_:_)
  |
8 | getMean [] = 0
  | ^^^^^^^^^^^^^^^...
 -}