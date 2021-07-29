use> read nocycle01.cmd
nocycle01.cmd> open nocycle01.use
nocycle01.cmd>
nocycle01.cmd> !create x:A
nocycle01.cmd> !insert (x,x) into AC
               Error: Object `x' cannot be a part of itself.
nocycle01.cmd> !create y:A
nocycle01.cmd> !insert (x,y) into AC
nocycle01.cmd> !insert (y,x) into AC
               Error: Detected a cycle in aggregation hierarchy. Object `y' is already a part of `x'.

use> read nocycle02.cmd
nocycle02.cmd> open nocycle02.use
nocycle02.cmd>
nocycle02.cmd> !create x:A
nocycle01.cmd> !insert (x,x) into AC
               Error: Object `x' cannot be a part of itself.
nocycle02.cmd> !create y:A
nocycle02.cmd> !insert (x,y) into AC
nocycle02.cmd> !insert (y,x) into AC
               Error: Detected a cycle in aggregation hierarchy. Object `y' is already a part of `x'.

use> read nocycle03.cmd
nocycle03.cmd> open nocycle03.use
nocycle03.cmd>
nocycle03.cmd> !create x:A
nocycle03.cmd> !create y:B
nocycle03.cmd> !insert (x,y) into AC1
nocycle03.cmd> !insert (y,x) into AC2
               Error: Detected a cycle in aggregation hierarchy. Object `y' is already a part of `x'.

use> read nocycle04.cmd
nocycle04.cmd> open nocycle04.use
nocycle04.cmd>
nocycle04.cmd> !create x:A
nocycle04.cmd> !create y:A
nocycle04.cmd> !insert (x,y) into AC1
nocycle04.cmd> !insert (y,x) into AC2
               Error: Detected a cycle in aggregation hierarchy. Object `y' is already a part of `x'.

use> read nocycle05.cmd
nocycle05.cmd> open nocycle05.use
nocycle05.cmd>
nocycle05.cmd> !create x:A
nocycle05.cmd> !create y:B
nocycle05.cmd> !insert (x,y) into AC1
nocycle05.cmd> !insert (y,x) into AC2
               Error: Detected a cycle in aggregation hierarchy. Object `y' is already a part of `x'.

use> read nocycle06.cmd
nocycle06.cmd> open nocycle06.use
nocycle06.cmd>
nocycle06.cmd> !create x:A
nocycle06.cmd> !create y:A
nocycle06.cmd> !insert (x,y) into AC1
nocycle06.cmd> !insert (y,x) into AC2
               Error: Detected a cycle in aggregation hierarchy. Object `y' is already a part of `x'.

use> read nocycle07.cmd
nocycle07.cmd> open nocycle07.use
nocycle07.cmd>
nocycle07.cmd> !create x:A
nocycle07.cmd> !create y:B
nocycle07.cmd> !insert (x,y) into AC1
nocycle07.cmd> !insert (y,x) into AC2
               Error: Detected a cycle in aggregation hierarchy. Object `y' is already a part of `x'.

use> read nocycle08.cmd
nocycle08.cmd> open nocycle08.use
nocycle08.cmd>
nocycle08.cmd> !create x:A
nocycle08.cmd> !create y:A
nocycle08.cmd> !insert (x,y) into AC1
nocycle08.cmd> !insert (y,x) into AC2
               Error: Detected a cycle in aggregation hierarchy. Object `y' is already a part of `x'.
