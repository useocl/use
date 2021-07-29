use> read noshare01.cmd
noshare01.cmd> open noshare01.use
noshare01.cmd>
noshare01.cmd> !create x:A
noshare01.cmd> !create y:A
noshare01.cmd> !create z:A
noshare01.cmd> !insert (x,z) into AC
noshare01.cmd> !insert (y,z) into AC
               Error: Object `z' is already a part of other composition.

use> read noshare02.cmd
noshare02.cmd> open noshare02.use
noshare02.cmd>
noshare02.cmd> !create x:A
noshare02.cmd> !create y:A
noshare02.cmd> !create z:B
noshare02.cmd> !insert (x,z) into AC
noshare02.cmd> !insert (y,z) into AC
               Error: Object `z' is already a part of other composition.

use> read noshare03.cmd
noshare03.cmd> open noshare03.use
noshare03.cmd>
noshare03.cmd> !create x:A
noshare03.cmd> !create y:A
noshare03.cmd> !create z:A
noshare03.cmd> !insert (x,z) into AC1
noshare03.cmd> !insert (y,z) into AC2
               Error: Object `z' is already a part of other composition.

use> read noshare04.cmd
noshare04.cmd> open noshare04.use
noshare04.cmd>
noshare04.cmd> !create x:A
noshare04.cmd> !create y:A
noshare04.cmd> !create z:B
noshare04.cmd> !insert (x,z) into AC1
noshare04.cmd> !insert (y,z) into AC2
               Error: Object `z' is already a part of other composition.

use> read noshare05.cmd
noshare05.cmd> open noshare05.use
noshare05.cmd>
noshare05.cmd> !create x:A
noshare05.cmd> !create y:C
noshare05.cmd> !create z:B
noshare05.cmd> !insert (x,z) into AC1
noshare05.cmd> !insert (y,z) into AC2
               Error: Object `z' is already a part of other composition.

use> read noshare06.cmd
noshare06.cmd> open noshare06.use
noshare06.cmd>
noshare06.cmd> !create x:A
noshare06.cmd> !create z:A
noshare06.cmd> !insert (x,z) into AC1
noshare06.cmd> !insert (x,z) into AC2
               Error: Object `z' is already a part of other composition.

use> read noshare07.cmd
noshare07.cmd> open noshare07.use
noshare07.cmd>
noshare07.cmd> !create x:A
noshare07.cmd> !create z:B
noshare07.cmd> !insert (x,z) into AC1
noshare07.cmd> !insert (x,z) into AC2
               Error: Object `z' is already a part of other composition.
