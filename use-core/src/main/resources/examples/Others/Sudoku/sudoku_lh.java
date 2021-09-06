package examples.UML2Kodkod.furtherExamples.Sudoku;

import java.util.ArrayList;
import java.util.List;

import kodkod.ast.Expression;
import kodkod.ast.Formula;
import kodkod.ast.IntConstant;
import kodkod.ast.IntExpression;
import kodkod.ast.Relation;
import kodkod.ast.Variable;
import kodkod.engine.Solution;
import kodkod.engine.Solver;
import kodkod.engine.satlab.SATFactory;
import kodkod.instance.Bounds;
import kodkod.instance.TupleFactory;
import kodkod.instance.TupleSet;
import kodkod.instance.Universe;

// Glatt machen und nach Regeln des Dokuments gestalten. 
// (umbenennen in sudoku Verweis auf Lars als Autor des use files)

// Zugehörige use/cmd/assl files ansehen und ggf. anpassen/dokumentieren.

//  die txt-/pdf-Dateien Dokumentieren / säubern etc.

public final class sudoku_lh {

	// WICHTIG: Partielle Lösungen werden später in USE als Objektdiagramm
	// vorgegeben.
	// Übersetzung muss dann geschrieben werden.
	// Alles was nicht als partielle Lösung vorgegeben wird: nur Constraints!
	// (?) oder
	// auch schon Wissen in die bounds?

	// UML-Classes: Each class becomes a relation.
	private final Relation Board, Row, Column, Square, Field;

	private final Relation Integer;

	// UML-Attributes
	private final Relation value;

	// UML-Associations: Each association becomes a relation.
	private final Relation BoardRows, BoardColumns, BoardSquares, BoardFields, RowFields,
			ColumnFields, SquareFields;

	public sudoku_lh() {

		// UML-Classes: Classes are unary relations, i.e., sets of atoms.
		this.Board = Relation.unary("Board");
		this.Row = Relation.unary("Row");
		this.Column = Relation.unary("Column");
		this.Square = Relation.unary("Square");
		this.Field = Relation.unary("Field");

		this.Integer = Relation.unary("Integer");

		// UML-Attributes
		this.value = Relation.binary("value");

		// UML-Associations: n-ary association are n-ary relations, i.e., sets
		// of tuples.
		this.BoardRows = Relation.binary("BoardRows");
		this.BoardColumns = Relation.binary("BoardColumns");
		this.BoardSquares = Relation.binary("BoardSquares");
		this.BoardFields = Relation.binary("BoardFields");
		this.RowFields = Relation.binary("RowFields");
		this.ColumnFields = Relation.binary("ColumnFields");
		this.SquareFields = Relation.binary("SquareFields");
	}

	// value (Attribute)
	public final Formula valueDom() {
		return value.join(Expression.UNIV).in(Field);
	}

	public final Formula valueRange() {
		return Expression.UNIV.join(value).in(Integer);
	}

	public final Formula valueAttribute() {
		// eigentlich lone?
		Variable f = Variable.unary("f");
		return f.join(value).one().forAll(f.oneOf(Field));
	}

	// BoardRows
	// board has Type Board
	public final Formula boardType_br() {
		return BoardRows.join(Expression.UNIV).in(Board);
	}

	// board[1]
	public final Formula board_br1_1() {
		Variable r = Variable.unary("r");
		return BoardRows.join(r).one().forAll(r.oneOf(Row));
	}

	// rows has Type Row
	public final Formula rowsType() {
		return Expression.UNIV.join(BoardRows).in(Row);
	}

	// rows[9]
	public final Formula rows9_9() {
		Variable b = Variable.unary("b");
		return b.join(BoardRows).count().lt(IntConstant.constant(9)).forAll(
				b.oneOf(Board));
	}

	// BoardColumns
	// board has Type Board
	public final Formula boardType_bc() {
		return BoardColumns.join(Expression.UNIV).in(Board);
	}

	// board[1]
	public final Formula board_bc1_1() {
		Variable c = Variable.unary("c");
		return BoardColumns.join(c).one().forAll(c.oneOf(Column));
	}

	// colmun has Type Column
	public final Formula columnsType() {
		return Expression.UNIV.join(BoardColumns).in(Column);
	}

	// columns[9]
	public final Formula columns9_9() {
		Variable b = Variable.unary("b");
		return b.join(BoardColumns).count().eq(IntConstant.constant(9)).forAll(
				b.oneOf(Board));
	}

	// BoardSquares
	// board has Type Board
	public final Formula boardType_bs() {
		return BoardSquares.join(Expression.UNIV).in(Board);
	}

	// board[1]
	public final Formula board_bs1_1() {
		Variable s = Variable.unary("s");
		return BoardSquares.join(s).one().forAll(s.oneOf(Square));
	}

	// squares has Type Square
	public final Formula squaresType() {
		return Expression.UNIV.join(BoardSquares).in(Square);
	}

	// rows[9]
	public final Formula squares9_9() {
		Variable b = Variable.unary("b");
		return b.join(BoardSquares).count().eq(IntConstant.constant(9)).forAll(
				b.oneOf(Board));
	}

	// BoardFields
	// board has Type Board
	public final Formula boardType_bf() {
		return BoardFields.join(Expression.UNIV).in(Board);
	}

	// board[1]
	public final Formula board_bf1_1() {
		Variable f = Variable.unary("f");
		return BoardFields.join(f).one().forAll(f.oneOf(Field));
	}

	// fields has Type Field
	public final Formula fieldsType() {
		return Expression.UNIV.join(BoardFields).in(Field);
	}

	// rows[9]
	public final Formula fields9_9() {
		Variable b = Variable.unary("b");
		return b.join(BoardFields).count().eq(IntConstant.constant(81)).forAll(
				b.oneOf(Board));
	}

	// RowFields
	// row has Type Row
	public final Formula rowType() {
		return RowFields.join(Expression.UNIV).in(Row);
	}

	// row[1]
	public final Formula row1_1() {
		Variable f = Variable.unary("f");
		return RowFields.join(f).one().forAll(f.oneOf(Field));
	}

	// fields has Type Field
	public final Formula fieldsType_rf() {
		return Expression.UNIV.join(RowFields).in(Field);
	}

	// fields[9]
	public final Formula fields_rf9_9() {
		Variable r = Variable.unary("r");
		return r.join(RowFields).count().eq(IntConstant.constant(9)).forAll(r.oneOf(Row));
	}

	// ColumnFields
	// column has Type Column
	public final Formula columnType() {
		return ColumnFields.join(Expression.UNIV).in(Column);
	}

	// row[1]
	public final Formula column1_1() {
		Variable f = Variable.unary("f");
		return ColumnFields.join(f).one().forAll(f.oneOf(Field));
	}

	// fields has Type Field
	public final Formula fieldsType_cf() {
		return Expression.UNIV.join(ColumnFields).in(Field);
	}

	// fields[9]
	public final Formula fields_cf9_9() {
		Variable c = Variable.unary("c");
		return c.join(ColumnFields).count().eq(IntConstant.constant(9)).forAll(
				c.oneOf(Column));
	}

	// SquareFields
	// square has Type Square
	public final Formula squareType() {
		return SquareFields.join(Expression.UNIV).in(Square);
	}

	// row[1]
	public final Formula square1_1() {
		Variable f = Variable.unary("f");
		return SquareFields.join(f).one().forAll(f.oneOf(Field));
	}

	// fields has Type Field
	public final Formula fieldsType_sf() {
		return Expression.UNIV.join(SquareFields).in(Field);
	}

	// fields[9]
	public final Formula fields_sf9_9() {
		Variable s = Variable.unary("s");
		return s.join(SquareFields).count().eq(IntConstant.constant(9)).forAll(
				s.oneOf(Square));
	}

	// context Field inv allowedValue:
	// self.value >= 1 and self.value <= 9
	// bereits modelliert (valueRange s.o. und bound s.u.)
	// vielleicht offener definieren und alles in constraints?
	// dann nur partielle Lösung in bounds definieren? kein weiteres Wissen?

	// context Row inv uniqueValuesRow:
	// self.fields->isUnique(value)
	// = self.fields->forAll(f1, f2 | f1<>f2 implies f1.value <> f2.value)
	// = self.fields->forAll(f1| self.fields->forAll(f2 | f1<>f2 implies
	// f1.value <> f2.value))
	// Überarbeiten! Navigation zu Field fehlt!
	public final Formula uniqueValuesRow() {
		Variable r = Variable.unary("r");

		Expression self_fields = r.join(RowFields);

		Variable f1 = Variable.unary("f1");
		Variable f2 = Variable.unary("f2");
		Formula implies = f1.eq(f2).not()
				.implies(f1.join(value).eq(f2.join(value)).not());

		// Kann man forAll(f1, f2 |...) direkt darstellen, oder immer aufspalten
		// s.o.?
		Formula forAll_inner = implies.comprehension(f2.oneOf(self_fields)).count().eq(
				self_fields.count());
		Formula forAll_outer = forAll_inner.comprehension(f1.oneOf(self_fields)).count()
				.eq(self_fields.count());

		return forAll_outer.forAll(r.oneOf(Row));

	}

	// context Column inv uniqueValuesColumn:
	// self.fields->isUnique(value)
	public final Formula uniqueValuesColumn() {
		Variable c = Variable.unary("c");

		Expression self_fields = c.join(ColumnFields);

		Variable f1 = Variable.unary("f1");
		Variable f2 = Variable.unary("f2");
		Formula implies = f1.eq(f2).not()
				.implies(f1.join(value).eq(f2.join(value)).not());

		// Kann man forAll(f1, f2 |...) direkt darstellen, oder immer aufspalten
		// s.o.?
		Formula forAll_inner = implies.comprehension(f2.oneOf(self_fields)).count().eq(
				self_fields.count());
		Formula forAll_outer = forAll_inner.comprehension(f1.oneOf(self_fields)).count()
				.eq(self_fields.count());

		return forAll_outer.forAll(c.oneOf(Column));

	}

	// context Square inv uniqueValuesSquare:
	// self.fields->isUnique(value)
	public final Formula uniqueValuesSquare() {
		Variable s = Variable.unary("s");

		Expression self_fields = s.join(SquareFields);

		Variable f1 = Variable.unary("f1");
		Variable f2 = Variable.unary("f2");
		Formula implies = f1.eq(f2).not()
				.implies(f1.join(value).eq(f2.join(value)).not());

		// Kann man forAll(f1, f2 |...) direkt darstellen, oder immer aufspalten
		// s.o.?
		Formula forAll_inner = implies.comprehension(f2.oneOf(self_fields)).count().eq(
				self_fields.count());
		Formula forAll_outer = forAll_inner.comprehension(f1.oneOf(self_fields)).count()
				.eq(self_fields.count());

		return forAll_outer.forAll(s.oneOf(Square));

	}

	public Bounds bounds_sudoku() {

		final List<String> atoms = new ArrayList<String>(5);

		for (int i = 1; i < 10; i++) {
			atoms.add("I_" + i);
			atoms.add("Row" + i);
			atoms.add("Column" + i);
			atoms.add("Square" + i);
		}

		atoms.add("board");

		for (int i = 1; i < 82; i++) {
			atoms.add("Field" + i);
		}

		// Creating the Universe
		final Universe u = new Universe(atoms);
		final TupleFactory f = u.factory();

		// Definition of Bounds (lower, upper)
		final Bounds b = new Bounds(u);

		// Constants (lower Bound = upper Bound)
		b.boundExactly(Board, f.setOf("board"));

		// final TupleSet values = f.noneOf(2);
		// values.add(f.tuple("Field6", "I_5"));
		// values.add(f.tuple("Field7", "I_7"));
		// values.add(f.tuple("Field10", "I_2"));
		// values.add(f.tuple("Field12", "I_7"));
		// values.add(f.tuple("Field13", "I_6"));
		// values.add(f.tuple("Field14", "I_1"));
		// values.add(f.tuple("Field17", "I_8"));
		// values.add(f.tuple("Field21", "I_8"));
		// values.add(f.tuple("Field23", "I_4"));
		// values.add(f.tuple("Field24", "I_9"));
		// values.add(f.tuple("Field34", "I_6"));
		// values.add(f.tuple("Field36", "I_8"));
		// values.add(f.tuple("Field37", "I_7"));
		// values.add(f.tuple("Field38", "I_9"));
		// values.add(f.tuple("Field41", "I_8"));
		// values.add(f.tuple("Field44", "I_5"));
		// values.add(f.tuple("Field45", "I_3"));
		// values.add(f.tuple("Field46", "I_5"));
		// values.add(f.tuple("Field47", "I_8"));
		// values.add(f.tuple("Field48", "I_6"));
		// values.add(f.tuple("Field52", "I_9"));
		// values.add(f.tuple("Field53", "I_7"));
		// values.add(f.tuple("Field56", "I_4"));
		// values.add(f.tuple("Field57", "I_3"));
		// values.add(f.tuple("Field60", "I_8"));
		// values.add(f.tuple("Field62", "I_2"));
		// values.add(f.tuple("Field67", "I_9"));
		// values.add(f.tuple("Field70", "I_3"));
		// values.add(f.tuple("Field72", "I_1"));
		// values.add(f.tuple("Field74", "I_1"));
		// values.add(f.tuple("Field78", "I_2"));
		// values.add(f.tuple("Field80", "I_9"));
		// b.bound(value, values, f.allOf(2));

		final TupleSet ints = f.noneOf(1);
		for (int i = 1; i < 10; i++) {
			ints.add(f.tuple("I_" + i));
		}
		b.boundExactly(Integer, ints);

		b.bound(value, f.allOf(2));

		final TupleSet rows = f.noneOf(1);
		for (int i = 1; i < 10; i++) {
			rows.add(f.tuple("Row" + i));
		}
		b.boundExactly(Row, rows);

		final TupleSet columns = f.noneOf(1);
		for (int i = 1; i < 10; i++) {
			columns.add(f.tuple("Column" + i));
		}
		b.boundExactly(Column, columns);

		final TupleSet squares = f.noneOf(1);
		for (int i = 1; i < 10; i++) {
			squares.add(f.tuple("Square" + i));
		}
		b.boundExactly(Square, squares);

		final TupleSet fields = f.noneOf(1);
		for (int i = 1; i < 82; i++) {
			fields.add(f.tuple("Field" + i));
		}
		b.boundExactly(Field, fields);

		final TupleSet boardRows = f.noneOf(2);
		for (int i = 1; i < 10; i++) {
			boardRows.add(f.tuple("board", "Row" + i));
		}
		b.boundExactly(BoardRows, boardRows);

		final TupleSet boardColumns = f.noneOf(2);
		for (int i = 1; i < 10; i++) {
			boardColumns.add(f.tuple("board", "Column" + i));
		}
		b.boundExactly(BoardColumns, boardColumns);

		final TupleSet boardSquares = f.noneOf(2);
		for (int i = 1; i < 10; i++) {
			boardSquares.add(f.tuple("board", "Square" + i));
		}
		b.boundExactly(BoardSquares, boardSquares);

		final TupleSet boardFields = f.noneOf(2);
		for (int i = 1; i < 82; i++) {
			boardFields.add(f.tuple("board", "Field" + i));
		}
		b.boundExactly(BoardFields, boardFields);

		final TupleSet rowFields = f.noneOf(2);
		final TupleSet columnFields = f.noneOf(2);
		for (int i = 0; i < 81; i++) {
			columnFields.add(f.tuple("Column" + (1 + (i % 9)), "Field" + (i + 1)));
			rowFields.add(f.tuple("Row" + (1 + (i) / 9), "Field" + (1 + i)));
		}
		b.boundExactly(ColumnFields, columnFields);
		b.boundExactly(RowFields, rowFields);

		final TupleSet squareFields = f.noneOf(2);
		squareFields.add(f.tuple("Square1", "Field1"));
		squareFields.add(f.tuple("Square1", "Field2"));
		squareFields.add(f.tuple("Square1", "Field3"));
		squareFields.add(f.tuple("Square1", "Field10"));
		squareFields.add(f.tuple("Square1", "Field11"));
		squareFields.add(f.tuple("Square1", "Field12"));
		squareFields.add(f.tuple("Square1", "Field19"));
		squareFields.add(f.tuple("Square1", "Field20"));
		squareFields.add(f.tuple("Square1", "Field21"));

		squareFields.add(f.tuple("Square2", "Field4"));
		squareFields.add(f.tuple("Square2", "Field5"));
		squareFields.add(f.tuple("Square2", "Field6"));
		squareFields.add(f.tuple("Square2", "Field13"));
		squareFields.add(f.tuple("Square2", "Field14"));
		squareFields.add(f.tuple("Square2", "Field15"));
		squareFields.add(f.tuple("Square2", "Field22"));
		squareFields.add(f.tuple("Square2", "Field23"));
		squareFields.add(f.tuple("Square2", "Field24"));

		squareFields.add(f.tuple("Square3", "Field7"));
		squareFields.add(f.tuple("Square3", "Field8"));
		squareFields.add(f.tuple("Square3", "Field9"));
		squareFields.add(f.tuple("Square3", "Field16"));
		squareFields.add(f.tuple("Square3", "Field17"));
		squareFields.add(f.tuple("Square3", "Field18"));
		squareFields.add(f.tuple("Square3", "Field25"));
		squareFields.add(f.tuple("Square3", "Field26"));
		squareFields.add(f.tuple("Square3", "Field27"));

		squareFields.add(f.tuple("Square4", "Field28"));
		squareFields.add(f.tuple("Square4", "Field29"));
		squareFields.add(f.tuple("Square4", "Field30"));
		squareFields.add(f.tuple("Square4", "Field37"));
		squareFields.add(f.tuple("Square4", "Field38"));
		squareFields.add(f.tuple("Square4", "Field39"));
		squareFields.add(f.tuple("Square4", "Field46"));
		squareFields.add(f.tuple("Square4", "Field47"));
		squareFields.add(f.tuple("Square4", "Field48"));

		squareFields.add(f.tuple("Square5", "Field31"));
		squareFields.add(f.tuple("Square5", "Field32"));
		squareFields.add(f.tuple("Square5", "Field33"));
		squareFields.add(f.tuple("Square5", "Field40"));
		squareFields.add(f.tuple("Square5", "Field41"));
		squareFields.add(f.tuple("Square5", "Field42"));
		squareFields.add(f.tuple("Square5", "Field49"));
		squareFields.add(f.tuple("Square5", "Field50"));
		squareFields.add(f.tuple("Square5", "Field51"));

		squareFields.add(f.tuple("Square6", "Field34"));
		squareFields.add(f.tuple("Square6", "Field35"));
		squareFields.add(f.tuple("Square6", "Field36"));
		squareFields.add(f.tuple("Square6", "Field43"));
		squareFields.add(f.tuple("Square6", "Field44"));
		squareFields.add(f.tuple("Square6", "Field45"));
		squareFields.add(f.tuple("Square6", "Field52"));
		squareFields.add(f.tuple("Square6", "Field53"));
		squareFields.add(f.tuple("Square6", "Field54"));

		squareFields.add(f.tuple("Square7", "Field55"));
		squareFields.add(f.tuple("Square7", "Field56"));
		squareFields.add(f.tuple("Square7", "Field57"));
		squareFields.add(f.tuple("Square7", "Field64"));
		squareFields.add(f.tuple("Square7", "Field65"));
		squareFields.add(f.tuple("Square7", "Field66"));
		squareFields.add(f.tuple("Square7", "Field73"));
		squareFields.add(f.tuple("Square7", "Field74"));
		squareFields.add(f.tuple("Square7", "Field75"));

		squareFields.add(f.tuple("Square8", "Field58"));
		squareFields.add(f.tuple("Square8", "Field59"));
		squareFields.add(f.tuple("Square8", "Field60"));
		squareFields.add(f.tuple("Square8", "Field67"));
		squareFields.add(f.tuple("Square8", "Field68"));
		squareFields.add(f.tuple("Square8", "Field69"));
		squareFields.add(f.tuple("Square8", "Field76"));
		squareFields.add(f.tuple("Square8", "Field77"));
		squareFields.add(f.tuple("Square8", "Field78"));

		squareFields.add(f.tuple("Square9", "Field61"));
		squareFields.add(f.tuple("Square9", "Field62"));
		squareFields.add(f.tuple("Square9", "Field63"));
		squareFields.add(f.tuple("Square9", "Field70"));
		squareFields.add(f.tuple("Square9", "Field71"));
		squareFields.add(f.tuple("Square9", "Field72"));
		squareFields.add(f.tuple("Square9", "Field79"));
		squareFields.add(f.tuple("Square9", "Field80"));
		squareFields.add(f.tuple("Square9", "Field81"));
		b.boundExactly(SquareFields, squareFields);

		return b;
	}

	public static void main(String[] args) {
		final sudoku_lh model = new sudoku_lh();
		final Solver solver = new Solver();

		// Using MiniSat-Algorithm
		solver.options().setSolver(SATFactory.MiniSat);
		solver.options().setFlatten(true);

		final Formula preconditions = model.board_bc1_1().and(model.board_bf1_1()).and(
				model.board_br1_1()).and(model.board_bs1_1()).and(model.boardType_bc())
				.and(model.boardType_bf()).and(model.boardType_br()).and(
						model.boardType_bs()).and(model.column1_1()).and(
						model.columns9_9()).and(model.columnsType()).and(
						model.columnType()).and(model.fields9_9()).and(
						model.fields_cf9_9()).and(model.fields_rf9_9()).and(
						model.fields_sf9_9()).and(model.fieldsType()).and(
						model.fieldsType_cf()).and(model.fieldsType_rf()).and(
						model.fieldsType_sf()).and(model.row1_1()).and(model.rows9_9())
				.and(model.rowsType()).and(model.rowType()).and(model.square1_1()).and(
						model.squares9_9()).and(model.squaresType()).and(
						model.squareType()).and(model.valueDom()).and(model.valueRange())
				.and(model.valueAttribute());

		final Formula show = preconditions.and(model.uniqueValuesRow()).and(
				model.uniqueValuesColumn()).and(model.uniqueValuesSquare());
		final Solution sol = solver.solve(show, model.bounds_sudoku());

		// final Formula show = model.trainType();
		// final Solution sol = solver.solve(show,
		// model.bounds_test_trainType());

		System.out.println(show);
		System.out.println(sol);

	}
}
