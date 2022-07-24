import Text.Printf
class MyEq a where
  (===) :: a -> a -> Bool

instance (Eq a, Eq b) => MyEq (a, b) where
  (===) (a, b) (a', b') = a == a' && b == b'

instance (Show a, Show b) => PrintfArg (a, b) where
  formatArg (a, b) f str = formatArg (show (a, b)) f str

oneTuple :: (Int, Int)
oneTuple = (1, 3)
sameTuple = (1, 3)
differentTuple = (1, 2)
compareWith :: (Int, Int) -> IO()
compareWith other =
  let result = if oneTuple === other then "equal" else "different" 
  in
    putStrLn (printf "Comparing %s and %s and they are %s" oneTuple other result)

main = compareWith sameTuple >> compareWith differentTuple
