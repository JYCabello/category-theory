import Text.Printf
class MyEq a where
  (===) :: a -> a -> Bool

instance (Eq a, Eq b) => MyEq (a, b) where
  (===) (a, b) (a', b') = a == a' && b == b'


oneTuple :: (Int, Int)
oneTuple = (1, 3)
sameTuple = (1, 3)
differentTuple = (1, 2)

compareEqual =
  putStrLn (printf "Comparing %s %s" (show oneTuple) (show sameTuple))
  >>
    if oneTuple === sameTuple
    then putStrLn "Equal"
    else putStrLn "Different"

compareDifferent =
  putStrLn (printf "Comparing %v %s" (show oneTuple) (show differentTuple))
  >>
    if oneTuple === differentTuple
    then putStrLn "Equal"
    else putStrLn "Different"

main = compareEqual >> compareDifferent
