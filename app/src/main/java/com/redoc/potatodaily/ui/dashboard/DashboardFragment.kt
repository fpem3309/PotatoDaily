package com.redoc.potatodaily.ui.dashboard

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.redoc.potatodaily.R
import com.redoc.potatodaily.databinding.FragmentDashboardBinding
import com.redoc.potatodaily.db.BoardEntity
import com.redoc.potatodaily.ui.dashboard.DashAddActivity.Companion.TAG
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment(), RecyclerViewAdapter.RowClickListener {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    // 나중에 값이 설정될거라고 lateinit
    lateinit var viewModel: DashboardViewModel
    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    lateinit var monthData: Array<String>
    lateinit var yearData: Array<String>

    private val now: LocalDateTime = LocalDateTime.now()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        monthData = resources.getStringArray(R.array.MonthList)

        val monthAdapter= ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, monthData)
        binding.month.adapter = monthAdapter

        val nowMonth = now.toString().substring(6,7) // 현재 month
        binding.month.setSelection(nowMonth.toInt()-1)  // 현재 3월이면 -1해서 index값이 2인 3월이 spinner set


        yearData = resources.getStringArray(R.array.YearList)
        val yearAdapter= ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, yearData)
        binding.spinnerYear.adapter = yearAdapter
        val nowYear = now.toString().substring(0,4)
        binding.spinnerYear.setSelection(nowYear.toInt()-2021)


        // 뷰 모델 프로바이더를 통해 뷰모델 가져오기
        // 라이프사이클을 가지고 있는 녀석을 넣어줌(자기 자신)
        // 가져올 뷰모델 클래스를 넣어서 뷰모델 가져오기
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        return root
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "resume")

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = RecyclerViewAdapter(this@DashboardFragment)
            adapter = recyclerViewAdapter
        }

        adapterList()

        // spinner year
        binding.spinnerYear.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selYear = binding.spinnerYear.selectedItem.toString().substring(0,4)
                Log.e("year select", selYear)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.e("year select", "선태안함?")
            }

        }

        // spinner month 눌렀을때
        binding.month.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long) { adapterList() }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

    }

    fun adapterList(){

        val sleMonth = binding.month.selectedItem.toString().substring(0,1)
        val nullBoard: List<BoardEntity> = listOf(BoardEntity("", "기록이 없어요", "", "", "", "", "", "", "", "null"))

        // 뷰 모델이 가지고있는 값의 변경사항을 관찰할 수 있는 라이브 데이터를 옵저빙한다
        // 이때부턴 선택해놓은 spinner에 선택된 month 로
        viewModel.getMonthBoardObservers(sleMonth).observe(this, Observer {
            if (it.toString() == "[]"){
                recyclerViewAdapter.setListData(ArrayList(nullBoard))
            }else{
                recyclerViewAdapter.setListData(ArrayList(it)) //현재월을 set
            }
            recyclerViewAdapter.notifyDataSetChanged()
        })
    }

    override fun onDeleteBoardClickListener(board: BoardEntity) {

        var builder = AlertDialog.Builder(context)
        builder.setTitle("기록을 삭제할까요?")
        builder.setMessage("삭제시 복구 불가능합니다")

        var dialogListener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> viewModel.deleteBoard(board)
            }
        }
        builder.setPositiveButton("삭제하기",dialogListener)
        builder.setNegativeButton("취소",null)
        builder.show()
    }

    override fun onItemClickListener(board: BoardEntity) {
    }

    override fun onUpdateBoardListener(board: BoardEntity) {

        Log.d(TAG + "board", board.date)
        val intent = Intent(context, DashAddActivity::class.java)
        intent.apply {
            this.putExtra("update", board.date)
        }
        startActivity(intent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}