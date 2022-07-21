data MyOwnTuple a b = MyOwnTuple a b

instance (Eq a, Eq b) => Eq (MyOwnTuple a b) where
  (==) (MyOwnTuple a b) (MyOwnTuple a' b') = a == a' && b == b'


oneTuple = MyOwnTuple 1 3
sameTuple = MyOwnTuple 1 3
differentTuple = MyOwnTuple 1 2

main =
  if oneTuple == sameTuple
  then putStrLn "Equal"
  else putStrLn "Different"
