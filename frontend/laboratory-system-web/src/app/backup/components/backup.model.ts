export interface Backup {
  id: number;
  fileName: string;
  filePath: string;
  createdAt: string;
  size: number;
  status: 'SUCCESS' | 'FAILED';
  type: 'AUTOMATIC' | 'MANUAL';
  errorMessage: string | null;
}

export interface BackupConfiguration {
  id: number;
  enabled: boolean;
  intervalHours: number;
  dailyTime: string;
  backupDirectory: string;
  retentionDays: number;
}

export interface UpdateBackupConfigurationRequest {
  id: number;
  enabled: boolean;
  intervalHours: number;
  dailyTime: string;
  backupDirectory: string;
  retentionDays: number;
}
