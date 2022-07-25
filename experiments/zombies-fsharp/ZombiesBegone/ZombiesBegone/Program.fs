// This is an advanced twist about "SingleOrDefault" where we are interested
// on the possible reasons the element could not be returned.
type GetSingleResult<'a> =
| Found of 'a // Success
| Multiple of 'a list // Error
| EmptyList // Error
| NotFound // Error

// The implementation is specification. One just has to map the space state  to the output,
// there's not a single conditional to get wrong here and any test you add for this would
// just be a mirror of the specification.
let getSingle (f: 'a -> bool) (lst: 'a list) : GetSingleResult<'a> =
  match lst with
  | [] -> EmptyList
  | l ->
    List.filter f l
    |> function
    | [] -> NotFound
    | [a] -> Found a
    | a -> Multiple a

// Now we're forced to handle all the possible outcomes by the compiler, again, with no
// automated check involved beyond the compiler, let's go over it:
// Z: Covered (empty list in getSingle and all three possible error outcomes here)
// O: Covered, it's the Found in both functions.
// M: Multiple, indeed, we scan the collection once we know it has contents and it's an error
//    if multiple elements match
// B: Boundaries, this is an expression defining all the space state, boundaries included.
// I: Interface, the compiler is enforcing only valid data to come in and all possible behaviours
//    are part of the interface, no exception throwing, nothing hidden that requires you to look inside
//    or read the whole test suite to notice.
// E: Exercise exceptional behaviour. There's no exceptional behaviour we have though of, if there were,
//    we would make it part of the interface, feel free to challenge this point.
// S: Simple scenarios: Every line of the pattern matching is the definition of a possible scenario,
//    defining the domain of the function.
let renderGetSingle (f: int -> bool) (lst: int list) : string =
  match getSingle f lst with
  | Found i -> $"Found the single element {i} in {lst}"
  | EmptyList -> "Tried to find a single element in an empty list"
  | NotFound -> $"The element could not be found in {lst}"
  | Multiple m -> $"Tried to find a single element in {lst}, but found many {m}"

// Any missing match will fail to compile as long we add the <WarningsAsErrors>FS0025</WarningsAsErrors>
// flag to the project file. If I remove the "Multiple" scenario handler, I get this:
// Incomplete pattern matches on this expression. For example, the value 'Multiple (_)' may indicate a case not covered by the pattern(s)

renderGetSingle (fun n -> n < 5) [1;5;6]
|> printfn "%s" // Found the single element 1 in [1; 5; 6]

renderGetSingle (fun n -> n < 5) []
|> printfn "%s" // Tried to find a single element in an empty list

renderGetSingle (fun n -> n < 5) [6;7;8]
|> printfn "%s" // The element could not be found in [6; 7; 8]

renderGetSingle (fun n -> n < 5) [1;3;8]
|> printfn "%s" // Tried to find a single element in [1; 3; 8], but found many [1; 3]
