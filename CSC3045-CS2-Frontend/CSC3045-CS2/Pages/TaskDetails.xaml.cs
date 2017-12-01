using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Navigation;
using System.Collections.ObjectModel;
using CSC3045_CS2.Exception;
using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace CSC3045_CS2.Pages
{
    public partial class TaskDetails : BasePage
    {
        #region Private Variables

        private TaskClient _taskClient;

        #endregion

        #region Public Variables

        public Task CurrentTask { get; set; }

        public ObservableCollection<TaskEstimate> DailyEstimates { get; set; }

        #endregion

        public TaskDetails(Task currentTask)
        {
            InitializeComponent();
            CurrentPage = this.Title;
            DataContext = this;

            _taskClient = new TaskClient();

            CurrentTask = currentTask;

            try
            {
                List<TaskEstimate> dailyEstimateList = _taskClient.GetTaskEstimates(
                                                                        CurrentTask.UserStory.Project.Id,
                                                                        CurrentTask.UserStory.Id,
                                                                        CurrentTask.Id);

                // dailyEstimateList.RemoveAll(item => item.Date > DateTime.Now);
                DailyEstimates = new ObservableCollection<TaskEstimate>(dailyEstimateList);
            }
            catch (RestResponseErrorException ex)
            {
                MessageBoxUtil.ShowErrorBox(ex.Message);
            }
        }

        #region Class Methods

        private void NumberOnlyTextBoxValidation(object sender, TextCompositionEventArgs e)
        {
            Regex regex = new Regex("[^0-9]+");
            e.Handled = regex.IsMatch(e.Text);
        }

        #endregion

        #region Command Methods

        public ICommand BackCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page sprintDashboard = new SprintDashboard(CurrentTask.UserStory.Sprint, false);

                    NavigationService.GetNavigationService(this).Navigate(sprintDashboard);
                });
            }
        }

        public ICommand SaveChangesCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    try
                    {
                        List<TaskEstimate> dailyEstimates = new List<TaskEstimate>(DailyEstimates);

                        _taskClient.UpdateTaskEstimates(
                                CurrentTask.UserStory.Project.Id,
                                CurrentTask.UserStory.Id,
                                CurrentTask.Id,
                                dailyEstimates);

                        MessageBoxUtil.ShowSuccessBox("Estimates updated Successfully");
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBoxUtil.ShowErrorBox(ex.Message);
                    }
                });
            }
        }

        public ICommand CascadeCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    TaskEstimate changedTask = (TaskEstimate)param;

                    foreach (TaskEstimate task in DailyEstimates)
                    {
                        if (task.Date > changedTask.Date)
                        {
                            task.Estimate = changedTask.Estimate;
                        }
                    }
                });
            }
        }

        #endregion
    }
}
