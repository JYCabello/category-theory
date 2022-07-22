import Text.Printf
class MyEq a where
  (===) :: a -> a -> Bool

instance (Eq a, Eq b) => MyEq (a, b) where
  (===) (a, b) (a', b') = a == a' && b == b'


oneTuple = (1, 3)
sameTuple = (1, 3)
differentTuple = (1, 2)

renderTuple :: (Int, Int) -> String
renderTuple (a, b) =
  printf "( %d %d )" a b


compareEqual =
  putStrLn ("Comparing " ++ renderTuple oneTuple ++ " " ++ renderTuple sameTuple)
  >>  if oneTuple === sameTuple
      then putStrLn "Equal"
      else putStrLn "Different"

compareDifferent =
  putStrLn ("Comparing " ++ renderTuple oneTuple ++ " " ++ renderTuple differentTuple)
  >>  if oneTuple === differentTuple
      then putStrLn "Equal"
      else putStrLn "Different"

main = compareEqual >> compareDifferent
