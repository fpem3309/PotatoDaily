package com.redoc.potatodaily.ui.dashboard

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import androidx.viewpager2.widget.ViewPager2
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

        // 뷰 모델 프로바이더를 통해 뷰모델 가져오기
        // 라이프사이클을 가지고 있는 녀석을 넣어줌(자기 자신)
        // 가져올 뷰모델 클래스를 넣어서 뷰모델 가져오기
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        val nowMonth = now.toString().substring(6,7) // 현재 month
        binding.month.setSelection(nowMonth.toInt()-1)  // 현재 3월이면 -1해서 index값이 2인 3월이 spinner set

        var month = viewModel.getDayBoardObservers(nowMonth) //현재월의 board

        // 뷰 모델이 가지고있는 값의 변경사항을 관찰할 수 있는 라이브 데이터를 옵저빙한다
        viewModel.getAllBoardObservers().observe(this@DashboardFragment, Observer {
            recyclerViewAdapter.setListData(month as ArrayList<BoardEntity>) //현재월을 set
            recyclerViewAdapter.notifyDataSetChanged()
        })


        // spinner month 눌렀을때
        binding.month.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) Toast.makeText(context, monthData[position], Toast.LENGTH_SHORT)
                    .show()

                month = viewModel.getDayBoardObservers(monthData[position].substring(0,monthData[position].length-1))

                Log.d("로그ㅋㅋㅋㅋ", month.toString())

                // 뷰 모델이 가지고있는 값의 변경사항을 관찰할 수 있는 라이브 데이터를 옵저빙한다
                viewModel.getAllBoardObservers().observe(this@DashboardFragment, Observer {
                    recyclerViewAdapter.setListData(month as ArrayList<BoardEntity>)
                    recyclerViewAdapter.notifyDataSetChanged()
                })
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }


    }

    override fun onDeleteBoardClickListener(board: BoardEntity) {

        var builder = AlertDialog.Builder(context)
        builder.setTitle("기록을 삭제할까요?")
        builder.setMessage("삭제시 복구 불가능합니다")

        var dialog_listener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> viewModel.deleteBoard(board)
            }
        }
        builder.setPositiveButton("삭제하기",dialog_listener)
        builder.setNegativeButton("취소",null)
        builder.show()
    }

    override fun onItemClickListener(board: BoardEntity) {
//        binding.name.setText(board.title)
//        binding.email.setText(board.content)
//
//        binding.name.setTag(binding.name.id, board.id)
//
//        binding.btnSave.setText("Update")
    }

    override fun onUpdateBoardListener(board: BoardEntity) {

        Log.d(TAG + "board", board.date)
        var intent = Intent(context, DashAddActivity::class.java)
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