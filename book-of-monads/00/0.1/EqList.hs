import Text.Printf
eqListTailRec :: Eq a => Bool -> [a] -> [a] -> Bool
eqListTailRec False _ _ = False
eqListTailRec True [] [] = True
eqListTailRec True (a1:a1t) (a2: a2t) = eqListTailRec (a1 == a1) a1t a2t
eqListTailRec _ _ _ = False
eqListTail :: Eq a => [a] -> [a] -> Bool
eqListTail = eqListTailRec True


class MyEq a where
  (===) :: a -> a -> Bool

instance Eq a => MyEq [a] where
  (===) = eqListTail

main = 
  let result = if ([] :: [Int]) === [] then "Equal" else "Different" in
  printf result